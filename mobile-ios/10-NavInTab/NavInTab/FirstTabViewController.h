//
//  FirstTabViewController.h
//  NavInTab
//
//  Created by exo on 2/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class StepTwoViewController;

@interface FirstTabViewController : UIViewController

@property (nonatomic, strong) StepTwoViewController *step2Controller; 

- (IBAction)takeNextStep:(id)sender;

@end
