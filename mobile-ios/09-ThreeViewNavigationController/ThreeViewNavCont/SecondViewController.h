//
//  SecondViewController.h
//  ThreeViewNavCont
//
//  Created by exo on 2/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ThirdViewController;

@interface SecondViewController : UIViewController

@property (nonatomic, retain) IBOutlet UIBarButtonItem *navBut;
@property (nonatomic, retain) ThirdViewController *thirdViewCtrl;
- (IBAction)moveToNextView:(id)sender;

@end
