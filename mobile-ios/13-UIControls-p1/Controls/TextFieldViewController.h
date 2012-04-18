//
//  TextFieldViewController.h
//  Controls
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TextFieldViewController : UIViewController 
@property (retain, nonatomic) IBOutlet UITextField *numberField;
@property (retain, nonatomic) IBOutlet UITextField *txtField;
- (IBAction)numberEditEnd:(id)sender;
- (IBAction)txtEditEnd:(id)sender;

@end
