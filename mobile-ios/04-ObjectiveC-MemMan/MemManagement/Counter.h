//
//  Counter.h
//  MemManagement
//
//  Created by exo on 2/6/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Counter : NSObject {
    NSNumber *_count;
}

// strong reference
@property (nonatomic, retain) NSNumber *count;

- (id)initWithCount:(NSNumber*)startingCount;
- (void)reset;

@end
