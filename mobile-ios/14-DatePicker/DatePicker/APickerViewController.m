//
//  APickerViewController.m
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "APickerViewController.h"

@implementation APickerViewController
@synthesize pickerView;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        self.tabBarItem.title = @"Picker";
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
    _data = [[NSArray alloc] initWithObjects:
              @"Red",@"Yellow",@"Green",@"Blue", @"Purple", @"Orange",
              @"Black", @"Gray", @"Tan", @"Pink", @"Coral", nil];
    _data2 = [[NSArray alloc] initWithObjects:
              @"Very Dark", @"Dark", @"Normal", @"Light", @"Very Light", nil];
}

- (void)viewDidUnload
{
    [self setPickerView:nil];
    [_data release];
    _data = nil;
    [_data2 release];
    _data2 = nil;
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
    [pickerView release];
    [_data release];
    [_data2 release];
    [super dealloc];
}

#pragma mark - UIPickerViewDelegate & UIPickerViewDataSource
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 2;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    if (component == 1) 
        return [_data2 count];
    return [_data count];
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    if (component == 0)
        return [_data objectAtIndex:row];
    return [_data2 objectAtIndex:row];
}

@end
