//
//  Greeting.h
//  ChapFour
//
//  Created by exo on 2/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol Greeting <NSObject>
@required
- (void)sayHello:(NSString*)name;
@end
