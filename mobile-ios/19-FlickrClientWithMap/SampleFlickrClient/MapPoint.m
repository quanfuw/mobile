//
//  MapPoint.m
//  SampleFlickrClient
//
//  Created by exo on 2/28/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "MapPoint.h"

@implementation MapPoint

@synthesize coordinate = _coordinate;
@synthesize title = _title;
@synthesize subtitle = _subtitle;
@synthesize data = _data;

- (id)initWithCoordinate:(CLLocationCoordinate2D)coordinate title:(NSString *)title subtitle:(NSString *)subtitle data:(NSDictionary *)data {
    if (self = [super init]) {
        _coordinate = coordinate;
        [self setTitle:title];
        [self setSubtitle:subtitle];
        [data retain];
        _data = data;
        
    }
    return self;
}

- (void)dealloc {
    [_title release];
    [_subtitle release];
    [_data release];
    [super dealloc];
}

@end
