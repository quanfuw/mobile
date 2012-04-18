//
//  TextFieldViewController.m
//  Controls
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "TextFieldViewController.h"

@implementation TextFieldViewController
@synthesize numberField;
@synthesize txtField;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    numberField.keyboardType = UIKeyboardTypeNumberPad;
    txtField.returnKeyType = UIReturnKeyDone;
}

- (void)viewDidUnload
{
    [self setNumberField:nil];
    [self setTxtField:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)dealloc {
    [numberField release];
    [txtField release];
    [super dealloc];
}
- (IBAction)numberEditEnd:(id)sender {
    [numberField resignFirstResponder];
}

- (IBAction)txtEditEnd:(id)sender {
    // close keyboard when press Done
    [sender resignFirstResponder];
}


@end
