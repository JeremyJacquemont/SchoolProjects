//
//  PopoverViewController.swift
//  TP-BDD-1
//
//  Created by iem on 06/05/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

class PopoverViewController: UIViewController {
    //MARK: - Variables
    var titles: [String]?
    
    @IBOutlet weak var button1: UIButton!
    @IBOutlet weak var button2: UIButton!
    @IBOutlet weak var button3: UIButton!
    
    
    //MARK: - Override Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let titlesButton = titles {
            if titlesButton.count  == 3 {
                button1.setTitle(titlesButton[0], forState: UIControlState.Normal)
                button2.setTitle(titlesButton[1], forState: UIControlState.Normal)
                button3.setTitle(titlesButton[2], forState: UIControlState.Normal)
            }
        }
    }

}
