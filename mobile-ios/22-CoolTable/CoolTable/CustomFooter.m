//
//  CustomFooter.m
//  CoolTable
//
//  Created by exo on 3/8/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "CustomFooter.h"
#import "Common.h"

@implementation CustomFooter

@synthesize lightGrayColor = _lightGrayColor;
@synthesize darkGrayColor = _darkGrayColor;

- (void)dealloc {
    [_lightGrayColor release];
    [_darkGrayColor release];
    [super dealloc];
}

- (id)init {
    if (self = [super init]) {
        self.backgroundColor = [UIColor clearColor];
        self.lightGrayColor = [UIColor colorWithRed:230.0/255.0 green:230.0/255.0 blue:230.0/255.0 alpha:1.0];
        self.darkGrayColor = [UIColor colorWithRed:187.0/255.0 green:187.0/255.0 
                                                    blue:187.0/255.0 alpha:1.0];
        
    }
    return self;
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
    }
    return self;
}

- (void)layoutSubviews {
    CGFloat paperRectMargin = 9.0;
    _colorBoxRect = CGRectMake(self.bounds.origin.x + paperRectMargin, self.bounds.origin.y, self.bounds.size.width - paperRectMargin * 2, self.bounds.size.height);
}

- (void)drawRect:(CGRect)rect {
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGColorRef shadowColor = [UIColor colorWithRed:0.2 green:0.2 
                                              blue:0.2 alpha:0.5].CGColor;
    CGRect arcRect = _colorBoxRect;
    arcRect.size.height = 8;
    
    
    CGContextSaveGState(context);
    CGContextSetShadowWithColor(context, CGSizeMake(0, 2), 3.0, shadowColor);
    CGMutablePathRef pathArc = createArcAtBottomOfRect(arcRect, 4.0);
    CGContextAddPath(context, pathArc);
    CGContextClip(context);
    CGContextSetFillColorWithColor(context, self.lightGrayColor.CGColor);
    CGContextFillRect(context, _colorBoxRect);
    drawLinearGradient(context, _colorBoxRect, self.lightGrayColor.CGColor, self.darkGrayColor.CGColor);
    CGContextRestoreGState(context);
    CGContextSaveGState(context);
    CGPoint pointA = CGPointMake(arcRect.origin.x, 
                                 arcRect.origin.y + arcRect.size.height - 1);
    CGPoint pointB = CGPointMake(arcRect.origin.x, arcRect.origin.y);
    CGPoint pointC = CGPointMake(arcRect.origin.x + arcRect.size.width - 1, 
                                 arcRect.origin.y);
    CGPoint pointD = CGPointMake(arcRect.origin.x + arcRect.size.width - 1, 
                                 arcRect.origin.y + arcRect.size.height - 1);
    draw1PxStroke(context, pointA, pointB, [UIColor whiteColor].CGColor);
    draw1PxStroke(context, pointC, pointD, [UIColor whiteColor].CGColor);    
    
//    CGContextAddRect(context, _colorBoxRect);
//    CGContextAddPath(context, pathArc);
//    CGContextEOClip(context);
//    CGContextAddPath(context, pathArc);
//    CGContextSetShadowWithColor(context, CGSizeMake(0, 2), 3.0, shadowColor);
//    CGContextFillPath(context);
    
    CGContextRestoreGState(context);
    
    
    CFRelease(pathArc);
    
    
}

@end
