//
//  CustomHeader.h
//  CoolTable
//
//  Created by exo on 3/8/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CustomHeader : UIView {
    CGRect _coloredBoxRect;
    CGRect _paperRect;
}

@property (nonatomic, retain) UILabel *titleLabel;
@property (nonatomic, retain) UIColor *lightColor;
@property (nonatomic, retain) UIColor *darkColor;


@end
