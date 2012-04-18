//
//  FirstViewController.h
//  ThreeViewNavCont
//
//  Created by exo on 2/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class SecondViewController;

@interface FirstViewController : UIViewController

@property (nonatomic, retain) SecondViewController *secondViewCtrl;
- (IBAction)moveToNextView:(id)sender;

@end
