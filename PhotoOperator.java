package utility;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public final class PhotoOperator {
	private PhotoOperator(){}
	
	/**
	 * 将文本内容打印到图片中
	 * 
	 * @param textList - 文本列表, 这是一个由Text类组成的List, 类Text保存着每条文本的文本,颜色和字体<br>
	 * @param directory - 存储目录<br>
	 * @param fileName - 图片名称<br>
	 * @param background - 背景色, 为空则使用默认值白色
	 * @param options - 其它参数,目前没用<br>
	 * @return String 图片的绝对路径
	 * @throws IOException 所有文本为空时
	 */
	public static String charactersToImage(List<Text> textList, String directory, String fileName, Color background, Map<String,String>options) throws IOException{
		File pictureFile = new File(directory,fileName+".jpg");
		Rectangle2D rectangleObject = null;
		int currentTextWidth = 0;
		int width = 0;
		int height = 0;
		for(int i=0,num=textList.size();i<num;i++){
			if("".equals(textList.get(i).getText())){
				continue;
			}
			rectangleObject = textList.get(i).getFont()
					                  .getStringBounds(textList.get(i).getText(),
					                		           new FontRenderContext(AffineTransform.getScaleInstance(1,1),
					                		           false,false));
			currentTextWidth = (int)Math.round(rectangleObject.getWidth());
			if(currentTextWidth > width){
				//图片的宽度为所有文本中最大的宽度
				width = currentTextWidth;
			}
			//高度需要累加
			height+=(int)Math.floor(rectangleObject.getHeight());
		}
		if(width == 0 || height == 0){
			//没有字符串,抛出异常
			throw new IOException("There is no text to be displayed");
		}
		width++;
		height+=3;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics graphicsObject = image.getGraphics();
        if(background == null){
        	background = Color.WHITE;
        }
        graphicsObject.setColor(background);
        graphicsObject.fillRect(0,0,width,height);
        int y = 0;
        for(int i=0,sum=textList.size();i<sum;i++){
        	if("".equals(textList.get(i).getText())){
				continue;
			}
        	graphicsObject.setColor(textList.get(i).getColor());
            graphicsObject.setFont(textList.get(i).getFont());
            //下边两行决定了文本放置的绝对坐标
            y+=textList.get(i).getFont()
            		          .getStringBounds(textList.get(i).getText(),
            		        		   		   new FontRenderContext(AffineTransform.getScaleInstance(1,1),
            		        		   		   false,false)).getHeight();
            graphicsObject.drawString(textList.get(i).getText(), 0, y);
        }
        graphicsObject.dispose();
        ImageIO.write(image, "jpg", pictureFile);
        image.flush();
        return pictureFile.getAbsolutePath();
	}
}

/**
 * 
 * Text类，目前用于生成文字图片<br><br>
 * 
 * 成员变量:<br>
 * String text - 文本内容<br>
 * Color color - 文本颜色<br>
 * Font font   - 文本字体<br>
 *
 */
class Text{
	private String text;
	private Color color;;
	private Font font;
	public Text(String text, Color color, Font font){
		this.text = text;
		if(this.text == null){
			text = "";
		}
		this.color = color;
		if(this.color == null){
			this.color = Color.WHITE;
		}
		this.font = font;
		if(this.font == null){
			this.font = new Font("Serief",Font.BOLD,16);
		}
	}
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return this.text;
	}
	public void setColor(Color color){
		this.color = color;
	}
	public Color getColor(){
		return this.color;
	}
	public void SetFont(Font font){
		this.font = font;
	}
	public Font getFont(){
		return this.font;
	}
}