//
//  APickerViewController.h
//  DatePicker
//
//  Created by exo on 2/20/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface APickerViewController : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource> {
    NSArray *_data;
    NSArray *_data2;
}


@property (retain, nonatomic) IBOutlet UIPickerView *pickerView;

@end
