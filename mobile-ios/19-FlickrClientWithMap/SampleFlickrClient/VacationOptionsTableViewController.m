//
//  VacationDetailTableViewController.m
//  SampleFlickrClient
//
//  Created by exo on 3/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "VacationOptionsTableViewController.h"
#import "PlacesCoreDataTableViewController.h"
#import "TagsCoreDataTableViewController.h"

@implementation VacationOptionsTableViewController

@synthesize vacation = _vacation;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        _options = [[NSArray arrayWithObjects:@"Itinerary", @"Tag Search", nil] retain];
    }
    return self;
}

- (void)dealloc {
    [_options release];
    [_vacation release];
    [super dealloc];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return YES;
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_options count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Option Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    cell.textLabel.text = [_options objectAtIndex:indexPath.row];
    
    return cell;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == 0) {
        PlacesCoreDataTableViewController *placesController = [[[PlacesCoreDataTableViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
        placesController.vacationName = self.vacation;
        [self.navigationController pushViewController:placesController animated:YES];
    } else {
        TagsCoreDataTableViewController *tagsController = [[[TagsCoreDataTableViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
        tagsController.vacationName = self.vacation;
        [self.navigationController pushViewController:tagsController animated:YES];
    }
}

@end
