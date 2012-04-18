//
//  Simple.h
//  ChapFour
//
//  Created by exo on 2/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Greeting.h"

@interface Simple : NSObject <Greeting> {
    NSString* simpleName;
}
@property (retain) NSString* simpleName;
- (void)startPlay:(NSString*)title audienceNumbers:(int)value supportingActor:(NSString*)actorValue extrasNeeded:(int)extrasvalue;
@end
