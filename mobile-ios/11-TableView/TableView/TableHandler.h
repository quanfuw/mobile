//
//  TableHandler.h
//  TableProjectOne
//
//  Created by exo on 2/15/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TableHandler : NSObject <UITableViewDelegate, UITableViewDataSource>

@property (nonatomic, retain) NSArray *tableDataList;
- (void)fillList;
@end
