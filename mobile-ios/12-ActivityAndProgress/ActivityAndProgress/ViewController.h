//
//  ViewController.h
//  ActivityAndProgress
//
//  Created by exo on 2/17/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class PleaseWaitViewController;

@interface ViewController : UIViewController

@property (retain, nonatomic) PleaseWaitViewController *myWaitView;
@property (retain, nonatomic) IBOutlet UIActivityIndicatorView *myActivityView;

- (IBAction)doIt:(id)sender;
- (IBAction)doOther:(id)sender;
- (void)moveBar:(id)sender;
@end
