//
//  TableHandler.m
//  TableProjectOne
//
//  Created by exo on 2/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "TableHandler.h"

@implementation TableHandler

@synthesize tableDataList = _tableDataList;

- (void)dealloc {
    [_tableDataList release];
    [super dealloc];
}
- (void)fillList {
    self.tableDataList = [NSArray arrayWithObjects:@"Item One",
                          @"Item Two", @"Item Three", @"Item Four", @"Item Five", @"Item Six",
                          @"Item Seven", @"Item Eight", @"Item Nine", @"Item Ten", @"Item Eleven",
                          @"Item Twelve", @"Item Thirteen", @"Item Fourteen", @"Item Fifteen",
                          @"Item Sixteen", @"Item Seventeen", @"Item Eighteen", @"Item Nineteen",
                          @"Item Twenty", nil];
}

#pragma mark - UITableViewDataSource protocol methods

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.tableDataList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"acell"];
    if (!cell) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"acell"] autorelease];
    }
    
    cell.textLabel.text = [self.tableDataList objectAtIndex:indexPath.row];
    return cell;
}

@end
