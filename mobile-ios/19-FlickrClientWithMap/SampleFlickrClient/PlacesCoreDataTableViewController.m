//
//  PlacesCoreDataTableViewController.m
//  SampleFlickrClient
//
//  Created by exo on 3/7/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PlacesCoreDataTableViewController.h"
#import "VacationHelper.h"
#import "Place+Flickr.h"
#import "PhotosCoreDataTableViewController.h"

@implementation PlacesCoreDataTableViewController

@synthesize vacationName = _vacationName;

- (void)dealloc {
    [_vacationName release];
    [super dealloc];
}

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {

    }
    return self;
}

- (void)setupFetchedResultsController:(NSManagedObjectContext*)context { // attaches an NSFetchRequest to this UITableViewController 
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"Place"];
    request.sortDescriptors = [NSArray arrayWithObject:[NSSortDescriptor sortDescriptorWithKey:@"visitedDate" ascending:YES]];

    self.debug = YES;
    self.fetchedResultsController = [[[NSFetchedResultsController alloc] initWithFetchRequest:request managedObjectContext:context sectionNameKeyPath:nil cacheName:nil] autorelease];
}

#pragma mark - setter & getter


- (void)setVacationName:(NSString *)vacationName {
    [_vacationName release];
    [vacationName retain];
    _vacationName = vacationName;
    [VacationHelper openVacation:vacationName usingBlock:^(UIManagedDocument *document) {
        [self setupFetchedResultsController:document.managedObjectContext];
    }];
}

#pragma mark - table view delegate

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation {
    return YES;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *CellIdentifier = @"Place Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier] autorelease];
    }
    
    Place *place = [self.fetchedResultsController objectAtIndexPath:indexPath];
    
    cell.textLabel.text = place.name;
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    Place *place = [self.fetchedResultsController objectAtIndexPath:indexPath];
    PhotosCoreDataTableViewController *photosController = [[[PhotosCoreDataTableViewController alloc] initWithStyle:UITableViewStyleGrouped] autorelease];
    photosController.place = place;
    [self.navigationController pushViewController:photosController animated:YES];
}

@end
