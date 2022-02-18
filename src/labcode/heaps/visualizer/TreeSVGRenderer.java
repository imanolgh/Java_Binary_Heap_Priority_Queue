package heaps.visualizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeSVGRenderer {
	
	static int nodeWidth = 60;           // Width of each node
	static int nodeSpacing = nodeWidth;  // Left/Right and Up/Down spacing
	
	static int currentX = 0;  // Counter used by Knuth's spacing
	
//	private static final String TREES_CSS_PATH = "labs/heaps/visualizer/trees.css";
	private static final String TREES_CSS_PATH = "src/labcode/heaps/visualizer/trees.css";
	
	/**
	 *  A structure to contain data for Tree items
	 *
	 */
	private static class TreeItem {
		String e;
		String p;
		int x;
		int depth;
		boolean empty = false;
		TreeItem expected;
	}
	
	
	/**
	 * Recursive helper method for creating trees with Knuth's algorithm
	 * 
	 * Based on: https://llimllib.github.io/pymag-trees/
	 * 
	 * @param treeItems Array of tree items (assumed to represent complete binary tree)
     * @param position current position
     * @param depth current depth
     *
	 */
	public static void knuthLayoutHelper(ArrayList<TreeItem> treeItems, int position, int depth) {
		int lcl = 2*position+1;
		int rcl = 2*position+2;
		if(lcl<treeItems.size()) {
			knuthLayoutHelper(treeItems, lcl, depth+1);
		}
		TreeItem thisItem = treeItems.get(position);
		thisItem.x = currentX;
		thisItem.depth = depth;
		currentX += nodeSpacing; 
		if(rcl<treeItems.size()) {
			knuthLayoutHelper(treeItems, rcl, depth+1);
		}
		
	}

	
	
    /**
     * Generate lines representing tree.
     *
     * @param treeItems array of tree items to represent
     *
     * @return String representing tree, with appropriate lines and spacing
     */ 
	public static String drawLines(ArrayList<TreeItem> treeItems) {
		String lines = "";
		for(int i=0;i<treeItems.size();i++) {
			int lcl = 2*i+1;
			int rcl = 2*i+2;
			TreeItem parent = treeItems.get(i);
			
			int py = 2+nodeSpacing/2+parent.depth*nodeSpacing;
			// Children are 1 layer deeper
			int cy = py+nodeSpacing;
			
			if(lcl < treeItems.size()) {
				TreeItem child = treeItems.get(lcl);
				lines += line(parent.x, py, child.x, cy, "treeLine");
			}
			if(rcl < treeItems.size()) {
				TreeItem child = treeItems.get(rcl);
				lines += line(parent.x, py, child.x, cy, "treeLine");				
			}
		}
		return lines;
	}
	
    /**
     * Generate nodes representing tree items
     *
     * @param treeItems array of tree items to represent
     *
     * @return String representing tree, with appropriate nodes and spacing
     */ 
	public static String drawNodes(ArrayList<TreeItem> treeItems) {
		String nodes = "";
		for(TreeItem item: treeItems) {
			int py = 2+nodeSpacing/2+item.depth*nodeSpacing;
			nodes += pqNode(item.x, py, item);
		}
		return nodes;
	}	

    /**
     * Generate full diagram representing tree items
     *
     * @param treeItems array of tree items to represent
     *
     * @return String representing tree, with appropriate nodes, lines, and spacing
     */
	public static String knuthLayout(ArrayList<TreeItem> treeItems) {
		String drawing = "";
		int maxDepth = 0;
		if(treeItems.size()==0) {
			currentX = nodeSpacing/2+2;
			drawing = text(currentX, nodeSpacing/2+2, nodeWidth, "badText","EMPTY");
			currentX += nodeWidth;
			maxDepth = 0;
		} else {
			// Compute the positions using the Knuth algorithm
			// Setup
			currentX = nodeSpacing/2+2;
			// Recursive
			knuthLayoutHelper(treeItems, 0, 0);
			
			// We now have the max x in currentX
			maxDepth = (int)Math.ceil(Math.log(treeItems.size()) / Math.log(2));
			drawing = drawLines(treeItems) + drawNodes(treeItems);
		}
		
		// Return the drawing
		return startSVG(currentX-nodeSpacing/2+2, (maxDepth+1)*nodeSpacing+4) + drawing + endSVG();
	}
	
	
    /**
     * Generate list of tree items from string representation of them
     *
     * @param tree String representation of tree
     *
     * @return ArrayList of tree items represented by input string
     */
	private static ArrayList<TreeItem> treeItems(String tree) {
	    ArrayList<TreeItem> treeItems = new ArrayList<TreeItem>();

		// Misc: Elements can't contain commas;  Priorities can't contain end parens ")".
		Pattern pattern = Pattern.compile("\\(E=([^,]*), P=([^\\)]*)\\)|(null)\\s*");
	    Matcher matcher = pattern.matcher(tree);
	    while(matcher.find()) {
	    	TreeItem newNode = new TreeItem();
	    	//System.out.println("Item: " + matcher.group(0));
	    	if(matcher.group(3)!=null) {
	    		newNode.empty = true;
	    	} else {
	    		newNode.e = matcher.group(1);
	    		newNode.p = matcher.group(2);
	    		newNode.empty = false;
	    	}
	    	treeItems.add(newNode);
	    }
	    return treeItems;
	}
	
    /**
     * Pad out a given tree with expected tree items and return
     * (Knuth) string representation of tree
     *
     * @param given String representing a given tree
     * @param expected String representing an expected tree
     *
     * @return (Knuth) String representing new tree
     */
	public static String diffTree(String given, String expected) {
		// 1. Get all the items for each tree
	    ArrayList<TreeItem> expectedItems = treeItems(expected);
	    ArrayList<TreeItem> givenItems = treeItems(given);
	    
	    // 2. Pad out the smaller one
	    int larger = Math.max(expectedItems.size(), givenItems.size());
	    ArrayList<TreeItem> smaller = expectedItems.size() < givenItems.size() ? expectedItems : givenItems;
	    for(int i=smaller.size();i<larger;i++) {
	    	TreeItem ti = new TreeItem();
	    	ti.empty = true;
	    	smaller.add(ti);
	    }

	    // 3. Now connect the trees (expected will link to given)
	    for(int i=0;i<givenItems.size();i++) {
	    	givenItems.get(i).expected = expectedItems.get(i);
	    }
		
		return knuthLayout(givenItems);
	}
	
    /**
     * Convert string representing tree to Knuth-style tree layout string.
     *
     * @param tree String representing tree
     *
     * @return String representing Knuth-style layout of tree
     */	
	public static String stringToTree(String tree) {
		// 1. Identify the number of nodes in the tree (assumes complete binary tree

	    ArrayList<TreeItem> treeItems = treeItems(tree);
	    
	    // Render tree: Lines first then nodes
	    return knuthLayout(treeItems);
	}
	
    /**
     * Build tag-format representation of a circle
     *
     * @param cx x-coordinate of center of circle
     * @param cy y-coordinate of center of circle
     * @param radius radius of circle
     * @param style style of circle
     *
     * @return String representing circle in tag format
     */
	public static String circle(int cx, int cy, int radius, String style) {
		return String.format("<circle cx=\"%d\" cy=\"%d\" r=\"%d\" class=\"%s\" />\n", cx,cy,radius,style);
	}
	
	
    /**
     * Build tag-format representation of a line
     *
     * @param x1 x-coordinate of endpoint 1 of line
     * @param y1 y-coordinate of endpoint 1 of line
     * @param x2 x-coordinate of endpoint 2 of line
     * @param y2 y-coordinate of endpoint 2 of line
     * @param style style of line
     *
     * @return String representing line in tag format
     */
	public static String line(int x1, int y1, int x2, int y2, String style) {
		return String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" class=\"%s\" />\n", x1, y1, x2, y2, style);
	}

    /**
     * Build tag-format representation of a text box
     *
     * @param x x-coordinate of text
     * @param y y-coordinate of text
     * @param length length of text
     * @param style style of text
     * @param content text content
     *
     * @return String representing text in tag format
     */
	public static String text(int x, int y, int length, String style, String content) {
		return String.format("<text x=\"%d\" y=\"%d\" textLength=\"%d\" lengthAdjust=\"spacingAndGlyphs\" class=\"%s\" \">%s</text>\n",x, y, length, style, content);
	}
	
	/** Return the CSS style needed for these trees (needs to be added once per file)
     *
     * @return String representing CSS style for trees
     *
     * @throws java.io.IOException file read
     */
	public static String treeStyles() throws IOException {
		return  new String ( Files.readAllBytes( Paths.get(TREES_CSS_PATH) ) );
	}
	
	
    /** 
     * Generate html header that includes given styles
     *
     * @param styles styles to include
     *
     * @return HTML header string
     */
	public static String htmlStart(String styles) {
		return "<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<style>\n" + 
				styles + 
				"</style>\n" + 
				"</head>\n" + 
				"<body>";				
	}
	
	
    /**
     * Generate HTML footer string
     *
     * @return HTML footer string
     */
	public static String htmlEnd() {
		return "</body>\n" + 
			   "</html>\n";
	}
	/**
     * Build graphical representation of PQ node
	 * 
	 * @param cx x coordinate in SVG
	 * @param cy y coordinate in SVG
     *
	 * @param item tree item to represent
	 * @return String for SVG element
	 */
	public static String pqNode(int cx, int cy, TreeItem item) {
		int radius = nodeWidth/2;
		int halfR = radius/2;
		int textWidth = (int)Math.floor(Math.sqrt(7.0/16.0*radius*radius));
		
		
		boolean badNode = false;    // Is the node different from expected
		String nodeStyle = "";		// Node style modifiers (plural)
		String line = "";			// Line to include (or now) in node
		String nodeText = "";		// Text string(s) to include
		
		
		// 1. Node itself
	    // Check for missing item (only for diffs)
	    if(item.empty==true && item.expected!=null && item.expected.empty!=true) {
	    	nodeStyle = "missingNode ";
	    }			
	    
		// 2. Determine Labels 
		if(item.empty==true) {
			// Null Label case
			badNode = true;
			textWidth = radius;
			nodeText += text(cx, cy, textWidth, "nullText", "NULL"); 
			// No line
			
		} else {
			// Non-null: 
			
			String textStyle;
			
			// Identify Value 
			textStyle = "goodText";
			if(item.expected!=null && !item.e.equals(item.expected.e)) {
				textStyle = "badText";
				badNode = true;
			}
			nodeText += text(cx, cy+halfR, textWidth, textStyle, item.e);

			// Identify Priority
			// Identify Value 
			textStyle = "goodText";
			if(item.expected!=null && !item.p.equals(item.expected.p)) {
				textStyle = "badText";
				badNode = true;
			}
			nodeText +=  text(cx, cy-halfR, textWidth, textStyle, item.p);
			
			// Two pieces of text; Also add line
			line = line(cx-radius, cy, cx+radius, cy, "treeLine");  // Not really a tree line, but close enough
		}
		
	    nodeStyle += badNode ? "badNode" : "node";
	    
	    String circle = circle(cx,cy, radius, nodeStyle);
		
		// 3. Cross out? 
		String crossOut = "";
		if((item.empty==true && item.expected==null) || (item.empty==false && item.expected!=null && item.expected.empty==true)) {
			crossOut = line(cx-radius, cy-radius, cx+radius, cy+radius, "badLine") + line(cx+radius, cy-radius, cx-radius, cy+radius, "badLine");
		}
		return circle + line + crossOut + nodeText;
	}
	
	
    /**
     * Generate HTML code for node legend
     *
     * @return HTML code for legend
     */
	public static String nodeLegend() {
		String s = "<div class=\"legend\">" + 
				   "<h2>Legend</h2>" +
					"<table>" +
						"<thead>" + 
							"<th>Notation</th>" +
							"<th>Node Type</th>" +
						"</thead>" + 
							
						"<tbody>" + 
						//Normal Node
						"<tr>" +          
							"<td>" + stringToTree("(E=item, P=prior.)") + "</td>" + 
							"<td>Normal Node</td>" + 
						"</tr>" +  
				
						//Empty Tree
						"<tr>" +          
							"<td>" + stringToTree("") + "</td>" + 
							"<td>Empty Tree</td>" + 
						"</tr>" +  

						//Null Node
						"<tr>" +          
							"<td>" + stringToTree("null") + "</td>" + 
							"<td>Null Value in Tree </ br>(No Entry)</td>" + 
						"</tr>" +  
					
						//Priority Error
						"<tr>" +          
							"<td>" + diffTree("(E=1, P=1)","(E=1, P=0)") + "</td>" + 
							"<td>Incorrect Priority</td>" + 
						"</tr>" +  

						//Data Error
						"<tr>" +          
							"<td>" + diffTree("(E=1, P=1)","(E=0, P=1)") + "</td>" + 
							"<td>Incorrect Data</td>" + 
						"</tr>" +  

						//Missing Node
						"<tr>" +          
							"<td>" + diffTree("null", "(E=1, P=1)") + "</td>" + 
							"<td>Node Expected, but Missing</td>" + 
						"</tr>" +  
						"</tbody>" +						
					"</table></div>";
		return s;
	}
	
	
    /**
     * Generate HTML for start of SVG area
     *
     * @param width width of area
     * @param height height of area
     *
     * @return HTML for SVG start
     */
	public static String startSVG(int width, int height) {
		return String.format("<svg width=\"%s\" height=\"%s\">\n",width, height);
	}

    /**
     * Generate HTML for end of SVG area
     *
     * @return HTML for SVG end
     */
	public static String endSVG() {
		return "</svg>\n\n";
	}

	/**
     * Write string to file
     *
     * @param name filename where string contents will be written
     * @param contents string to write
     *
     * @throws java.io.IOException file write
     */
	public static void writeFile(String name, String contents) throws IOException {
		BufferedWriter out = null;

		try {
		    FileWriter fstream = new FileWriter("outputs/" + name + ".html", true); 
		    out = new BufferedWriter(fstream);
		    out.write(contents);
		} catch (IOException e) {
		    System.err.println("Error: " + e.getMessage());
		} finally {
		    if(out != null) {
		        out.close();
		    }
		}
	}

    /**
     * Display error message for an operation
     *
     * @param before state of PQ before operation
     * @param operation operation that triggered error
     * @param after state of PQ after operation
     * @param file filename where error contents will be logged
     *
     * @throws java.io.IOException file write
     */
	public static void showErrorNoDelta(String before, String operation, String after, String file) throws IOException {
		String contents;
			contents = htmlStart(treeStyles()) + 
					  "<h1 style=\"red;\">OPERATION INCORRECT!</h1><br />" +
					  "<h2>String representation of initial tree</h2><br />" +
					  "<pre>" + before + "</pre><br />" + 
					  "<h2>Initial tree</h2><br />" +
					  stringToTree(before) + "<br />" +

					  "<h2>Operation</h2><br>" +
					  "<code>" + operation + "</code>" + 
					  					  
					  "<h2>String representation of actual final tree produced</h2><br />" +
					  "<pre>" + after + "</pre><br />" + 
					  "<h2>Actual final tree produced</h2><br />" +
					  stringToTree(after) + "<br />" +
					  
			          htmlEnd();
			writeFile(file, contents);
	}
	
    /**
     * Display error message for an operation along with expected
     * state of PQ after operation
     *
     * @param before state of PQ before operation
     * @param operation operation that triggered error
     * @param expected expected state of PQ after operation
     * @param after actual state of PQ after operation
     * @param file filename where error contents will be logged
     *
     * @throws java.io.IOException file write
     */
	public static void showError(String before, String operation, String expected, String after, String file) throws IOException {
		String contents;
		if(after.equals(expected)) {
			contents = htmlStart(treeStyles()) + 
					  "<h1 style=\"color:green;\">SUCCESS!</h1><br />" +
					  "<h2>String representation of initial tree</h2><br />" +
					  "<pre>" + before + "</pre><br />" + 
					  "<h2>Initial tree</h2><br />" +
					  stringToTree(before) + "<br />" +

					  "<h2>Operation</h2><br>" +
					  "<code>" + operation + "</code>" + 
					  					  
					  "<h2>String representation of actual final tree produced</h2><br />" +
					  "<pre>" + after + "</pre><br />" + 
					  "<h2>Actual final tree produced</h2><br />" +
					  stringToTree(after) + "<br />" +
					  
			          htmlEnd();
			
		} else {
		
			contents = htmlStart(treeStyles()) + 
						  "<h1 style=\"color:red;\">OPERATION INCORRECT!</h1><br />" +
						  "<h2>String representation of initial tree</h2><br />" +
						  "<pre>" + before + "</pre><br />" + 
						  "<h2>Initial tree</h2><br />" +
						  stringToTree(before) + "<br />" +

						  "<h2>Operation</h2><br>" +
						  "<code>" + operation + "</code>" + 
						  
						  "<h2>String representation of expected tree</h2><br />" +
						  "<pre>" + expected + "</pre><br />" + 
						  "<h2>Expected tree</h2><br />" +
						  stringToTree(expected) + "<br />" +

						  
						  "<h2>String representation of actual final tree produced</h2><br />" +
						  "<pre>" + after + "</pre><br />" + 
						  "<h2>Actual final tree produced</h2><br />" +
						  stringToTree(after) + "<br />" +
						  
						  "<h2>Differences/errors</h2><br />" + 
						  diffTree(after,expected) + "<br />" +
						  nodeLegend() + 
				          htmlEnd();
		}
	
		writeFile(file, contents);
	}
	
	
    /**
     * Tests/debugging of other methods
     *
     * @param args Args to file (not used)
     *
     * @throws java.io.IOException file write
     */
	public static void main(String[] args) throws IOException {

		
//		stringToTree("E=12 E=4 E=6");
//		stringToTree("(E=1, P=2) (E=3, P=7) null (E=8, P=9)");
		//String t1 = "(E=1, P=2) (E=3, P=7)";
		String t1 =  "(E=1, P=a)  null        (E=13, P=1c) (E=13, P=huh) (E=7, P=te) ";
		String t1e = "(E=1, P=a) (E=13, P=1c)  null        (E=14, P=huh) (E=7, P=t) (E=9, P=77)";
//		String t1 = "(E=1, P=a) (E=3, P=bc) (E=13, P=1c) (E=13, P=huh)";
//		String t1 = "(E=1, P=a) null (E=13, P=1c) (E=13, P=huh)  (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh) (E=13, P=huh)";
//		writeFile("out", htmlStart(treeStyles()) + nodeLegend() + htmlEnd());
//	    out.write(htmlStart(treeStyles()) + nodeLegend() + htmlEnd());
//	    out.write(htmlStart(treeStyles()) + diffTree(t1,t1e) + htmlEnd());
//	    out.write(htmlStart(treeStyles()) + stringToTree(t1) + htmlEnd());
		// Before, Operation, After, Expected, out
		showError( "(E=1, P=1)", "insert(2,2)",  "(E=1, P=1) (E=2, P=2)", "(E=1, P=1) (E=1, P=1)", "out");

	}
	
}
