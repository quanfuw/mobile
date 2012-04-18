//
//  FirstTabViewController.m
//  NavInTab
//
//  Created by exo on 2/14/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FirstTabViewController.h"
#import "StepTwoViewController.h"

@implementation FirstTabViewController

@synthesize step2Controller = _step2Controller;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)dealloc {
    [_step2Controller release];
    [super dealloc];
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
    // Do any additional setup after loading the view from its nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)takeNextStep:(id)sender {
    if (!self.step2Controller) {
        self.step2Controller = [[[StepTwoViewController alloc] initWithNibName:@"StepTwoViewController" bundle:nil] autorelease];
    }
    [self.navigationController pushViewController:self.step2Controller animated:YES];
}

@end
