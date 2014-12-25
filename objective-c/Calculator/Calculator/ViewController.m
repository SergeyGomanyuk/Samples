//
//  ViewController.m
//  Calculator
//
//  Created by Sergey Gomanyuk on 18.09.14.
//  Copyright (c) 2014 sergg. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

enum {nope = 0, plus = 101, minus = 102, multi = 103, divi = 104};

double x = 0, y = 0;

int op = nope;

bool cleanTablo = false;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)clear:(id)sender {
    x = 0;
    [self tablo];
}

- (IBAction)allClear:(id)sender {
    x = 0;
    y = 0;
    [self tablo];
}

- (IBAction)inverse:(id)sender {
    x = -1 * x;
    [self tablo];
}

- (IBAction)operation:(id)sender {
    if(op != nope) [self equals: sender];
    y = x;
    op = [sender tag];
    cleanTablo = true;
}

- (IBAction)num:(id)sender {
    if(cleanTablo) { x = 0; cleanTablo = false; }
    x = (x*10) + [sender tag];
    [self tablo];
}

- (IBAction)equals:(id)sender {
    switch (op) {
        case plus:
            x = y + x;
            break;
        case minus:
            x = y - x;
            break;
        case multi:
            x = y * x;
            break;
        case divi:
            x = y / x;
            break;
        default:
            break;
    }
    op = nope;
    [self tablo];
    cleanTablo = true;
}

- (void)tablo {
    NSString *str = [NSString stringWithFormat: @"%g", x];
    [displayLabel setText:str];
}

@end
