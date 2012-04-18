//
//  DatePickerController.m
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "DatePickerController.h"

@implementation DatePickerController

@synthesize label = _label;
@synthesize myDatePicker = _myDatePicker;

- (void)dealloc {
    [_label release];
    [_myDatePicker release];
    [super dealloc];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.tabBarItem.title = @"Date Picker";
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - Private method
- (NSString*)stringFromDate:(NSDate*)date {
    NSDateFormatter *formatter = [[[NSDateFormatter alloc] init] autorelease];
    [formatter setDateFormat:@"MM/dd/yyyy 'at' HH:mm:ss"];
    return [formatter stringFromDate:date];    
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.label.text = [self stringFromDate:self.myDatePicker.date];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    [_label release];
    _label = nil;
    [_myDatePicker release];
    _myDatePicker = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)timeChanged:(id)sender {
    self.label.text = [self stringFromDate:self.myDatePicker.date];
}

@end
