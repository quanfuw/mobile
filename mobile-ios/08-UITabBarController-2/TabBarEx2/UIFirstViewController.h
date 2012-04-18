//
//  UIFirstViewController.h
//  TabBarEx2
//
//  Created by exo on 2/13/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIFirstViewController : UIViewController {
    int myIndex;
}

@property (retain, nonatomic) IBOutlet UILabel *label;
- (IBAction)addMoreTab:(id)sender;

@end
