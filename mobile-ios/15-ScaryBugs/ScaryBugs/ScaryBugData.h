//
//  ScaryBugData.h
//  ScaryBugs
//
//  Created by exo on 2/21/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ScaryBugData : NSObject
@property (nonatomic, strong) NSString *title;
@property (nonatomic, assign) float rating;
- (id)initWithTitle:(NSString*)title rating:(float)rating;
@end
