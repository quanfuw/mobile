//
//  SwitchSliderViewController.h
//  Controls
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SwitchSliderViewController : UIViewController

@property (retain, nonatomic) IBOutlet UISwitch *mySwitch;

- (IBAction)switchChanged:(id)sender;
- (IBAction)sliderChanged:(id)sender;

@end
