/***
 * 
 * Copyright (C) 2008 Alessandro La Rosa
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Contact: alessandro.larosa@gmail.com
 *
 * Author: Alessandro La Rosa
 */

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ScrollableImageCanvas extends Canvas
{
	// image-related properties
	int imageHeight = 0;
	int imageWidth = 0;
	Image image = null;
	
	//	touch properties
	protected int lastPointerX = -1;
	protected int lastPointerY = -1;
	
	// scroll properties
	protected int translationX = 0;
	protected int translationY = 0;
	
	public ScrollableImageCanvas(Image image)
	{
		this.image = image;
		this.imageWidth = image.getWidth();
		this.imageHeight = image.getHeight();
	}
	protected void paint(Graphics g)
	{
		g.setColor(0xffffff);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(image, - translationX, - translationY, Graphics.TOP | Graphics.LEFT);
	}
	protected void pointerPressed(int x, int y)
	{
		lastPointerX = x;
		lastPointerY = y;
	}
	protected void pointerReleased(int x, int y)
	{
		lastPointerX = -1;
		lastPointerY = -1;
	}
	protected void pointerDragged(int x, int y)
	{
		scrollImage(lastPointerX - x, lastPointerY - y);
		
		lastPointerX = x;
		lastPointerY = y;
	}
	void scrollImage(int deltaX, int deltaY)
	{
		if(imageWidth > getWidth())
		{
			translationX += deltaX;
			
			if(translationX < 0)
				translationX = 0;
			else if(translationX + getWidth() > imageWidth)
				translationX = imageWidth - getWidth();
		}
		
		if(imageHeight > getHeight())
		{
			translationY += deltaY;
			
			if(translationY < 0)
				translationY = 0;
			else if(translationY + getHeight() > imageHeight)
				translationY = imageHeight- getHeight();
		}
		
		repaint();
	}

}
