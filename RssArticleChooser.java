package com.website.rssreader;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*RssArticleChooser reads in a specified RSS Feed, and then parses out the Items within the feed.
 * Then it instantiates Item objects with Title and Link information, adding these Items to a List.
 * The List of Item Titles is printed to the Console. User chooses an Item Title. RssArticleChooser
 * then sends the corresponding link to open in a Web Browser.
*/


public class RssArticleChooser {
	
	public static void main(String[]args)
	{
		DocumentBuilderFactory factory = 
						DocumentBuilderFactory.newInstance();
		try{
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse("http://www.sdbj.com/rss/headlines/");
				
				Element root = doc.getDocumentElement();
				NodeList children = root.getChildNodes();
				Node channel = null;
			
			for(int i = 0 ; i < children.getLength(); i++)
			{
				if(children.item(i).getNodeName().equals("channel"))
				{
					channel = children.item(i);
					break;
				}
			}
			
			if (channel == null)
			{	
				System.out.println("No channel.");
				System.exit(1);
			}
			
			NodeList channelChildren = channel.getChildNodes();
	
			List<Item> itemList = new ArrayList<Item>();
	
			// Outer loop iterates through the XML, finds the first item.
			// When first item is found, the inner loop finds title and link of that item.
			// Then it finds the next item, finds title and link, and so on.
			for(int i = 0 ; i < channelChildren.getLength(); i++)
			{
				Node child = channelChildren.item(i);							// Node child instantiated with all child elements of channel element
				
				if (child instanceof Element)									// Make sure child is not white space
				{
					
					Element childElement = (Element) child;
			
					if(childElement.getNodeName().equals("item"))
					{
	
						NodeList itemChildren = childElement.getChildNodes();
						
						String title = null;
						String link = null;
						
						for(int ii = 0 ; ii < itemChildren.getLength(); ii++)
						{	
							
							Node itemChild = itemChildren.item(ii);
							if(itemChild instanceof Element)					// Make sure itemChild is not white space
							{
								Element e = (Element) itemChild;
								
								if(e.getNodeName().equals("title"))
								{
									title = e.getTextContent();
								}
								
								if(itemChild.getNodeName().equals("link"))
								{
									link = e.getTextContent();
								}
							}		
						}
						
						Item item = new Item(title, link);
						itemList.add(item);
						item = null;
						
					}
				}
			}
			
			int index = 1;
			
			while( index <= itemList.size())
			{
				System.out.println(index + ") " + itemList.get(index-1).getTitle());
				index++;
			}
					
			
			System.out.println("\nEnter the number that corresponds to the RSS Feed Title you wish to read more about.");
			
			Scanner scanny = new Scanner(System.in);
			int input = scanny.nextInt();
				
			while(input - 1 < 0 || input-1 > itemList.size())
			{
				System.out.println("You must enter a number from the list above. Try again.");
				input = scanny.nextInt();
			}
			
			String selectedItem = itemList.get(input-1).getLink();		
			Desktop.getDesktop().browse(new URI(selectedItem));	


		} catch(Exception e){
			e.printStackTrace();
		
		}
		
	}
}
