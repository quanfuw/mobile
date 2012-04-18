//
//  ViewController.h
//  Picnic
//
//  Created by exo on 3/9/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController {
    BOOL bugDead;
}
@property (retain, nonatomic) IBOutlet UIImageView *basketTop;
@property (retain, nonatomic) IBOutlet UIImageView *basketBottom;
@property (retain, nonatomic) IBOutlet UIImageView *napkinTop;
@property (retain, nonatomic) IBOutlet UIImageView *napkinBottom;
@property (retain, nonatomic) IBOutlet UIImageView *bug;

@end
