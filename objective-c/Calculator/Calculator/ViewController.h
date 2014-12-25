//
//  ViewController.h
//  Calculator
//
//  Created by Sergey Gomanyuk on 18.09.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController {
    
    IBOutlet UILabel *displayLabel;

}

- (IBAction)clear:(id)sender;

- (IBAction)allClear:(id)sender;

- (IBAction)inverse:(id)sender;

- (IBAction)operation:(id)sender;

- (IBAction)num:(id)sender;

- (IBAction)equals:(id)sender;

@end
