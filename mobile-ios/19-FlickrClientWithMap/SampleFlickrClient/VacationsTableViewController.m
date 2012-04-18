//
//  VacationsTableViewController.m
//  SampleFlickrClient
//
//  Created by exo on 3/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "VacationsTableViewController.h"
#import "VacationHelper.h"
#import "VacationOptionsTableViewController.h"

@interface VacationsTableViewController () 
@property (nonatomic, retain) NSArray *vacations;
@end

@implementation VacationsTableViewController 

@synthesize vacations = _vacations;

- (void)dealloc {
    [_vacations release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style {
    if (self = [super initWithStyle:style]) {
        self.tableView.delegate = self;
        self.navigationItem.title = @"Vacations";
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.vacations = [VacationHelper getVacations];
}

- (void)viewWillDisappear:(BOOL)animated {
    self.vacations = nil;
    [super viewWillDisappear:animated];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_vacations count];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"Vacation Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    cell.textLabel.text = [self.vacations objectAtIndex:indexPath.row];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *vacation = [self.vacations objectAtIndex:indexPath.row];
    VacationOptionsTableViewController *vacationDetail = [[[VacationOptionsTableViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
    vacationDetail.vacation = vacation;
    vacationDetail.navigationItem.title = vacation;
    [self.navigationController pushViewController:vacationDetail animated:YES];
}

@end
