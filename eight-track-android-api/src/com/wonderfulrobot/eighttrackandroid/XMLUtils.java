package com.wonderfulrobot.eighttrackandroid;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class XMLUtils {
    /*
     * Get Node text content
     * @since DOM Level 3
     */
    public static String getTextContent(Node n) throws DOMException {
        Node child = n.getFirstChild();
        if (child != null) {
            Node next = child.getNextSibling();
            if (next == null) {
                return hasTextContent(child) ? child.getNodeValue() : "";
            }
            StringBuffer buf = new StringBuffer();
            getTextContent(n,buf);
            return buf.toString();
        }
        return "";
    }

    // internal method taking a StringBuffer in parameter
    public static void getTextContent(Node n, StringBuffer buf) throws DOMException {
        Node child = n.getFirstChild();
        while (child != null) {
            if (hasTextContent(child)) {
            	String content = child.getNodeValue();
            	if (content != null) {
            		buf.append(content);
            	} 
            }
            child = child.getNextSibling();
        }
    }

    // internal method returning whether to take the given node's text content
    public static boolean hasTextContent(Node child) {
        return child.getNodeType() != Node.COMMENT_NODE &&
            child.getNodeType() != Node.PROCESSING_INSTRUCTION_NODE;
    }

}
