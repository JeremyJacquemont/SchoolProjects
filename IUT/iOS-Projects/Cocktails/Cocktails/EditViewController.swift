//
//  EditViewController.swift
//  Cocktails
//
//  Created by iem on 05/03/2015.
//  Copyright (c) 2015 JeremyJacquemont. All rights reserved.
//

import UIKit

// MARK: - EditViewControllerType
enum EditViewControllerType{
    case Add, Edit
}

class EditViewController: UIViewController {
    
    // MARK: - Variables
    @IBOutlet weak var ingredientsTextView: UITextView!
    @IBOutlet weak var directivesTextView: UITextView!
    @IBOutlet weak var nameTextField: UITextField!
    
    var type: EditViewControllerType?
    var cocktail: Cocktail?
    var lastViewController: UIViewController?

    // MARK: - UIViewController Override
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpContent()
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    // MARK: - Functions
    func setUpContent(){
        if let valueType = type {
            switch(valueType){
                case .Add:
                    addSetupContent()
                    break
                case .Edit:
                    editSetupContent()
                    break
                default:
                    break;
            }
        }
        buttonSetup()
    }
    func addSetupContent(){
        self.title = "Add Cocktail"
    }    
    func editSetupContent(){
        self.title = "Edit Cocktail"
        self.nameTextField.enabled = false
        
        if let valueCocktail = cocktail {
            nameTextField.text = valueCocktail.name
            ingredientsTextView.text = valueCocktail.ingredients
            directivesTextView.text = valueCocktail.directions
        }
    }    
    func buttonSetup(){
        let save = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Save, target: self, action: "changeContent")
        let cancel = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Cancel, target: self, action: "closeModal")
        self.navigationItem.setLeftBarButtonItem(cancel, animated: true)
        self.navigationItem.setRightBarButtonItem(save, animated: true)
    }
    func changeContent(){
        var manager = DBManager.sharedInstance
        
        if let valueType = type {
            switch(valueType){
            case .Add:
                if !nameTextField.text.isEmpty && !ingredientsTextView.text.isEmpty && !directivesTextView.text.isEmpty {
                    cocktail = Cocktail(name: nameTextField.text, ingredients: ingredientsTextView.text, directions: directivesTextView.text)
                    manager.addCocktail(cocktail!)
                    closeModal()
                }
                else {
                    showAlertView()
                }
                break
            case .Edit:
                if !ingredientsTextView.text.isEmpty && !directivesTextView.text.isEmpty {
                    let newCocktail = Cocktail(name: cocktail!.name, ingredients: ingredientsTextView.text, directions: directivesTextView.text)
                    manager.editCocktail(newCocktail)
                    closeModal()
                }
                else {
                    showAlertView()
                }
                break
            default:
                break;
            }
        }
    }
    func showAlertView() {
        var alert = UIAlertView(title: "An error occured", message: "Please, check all values are not empty!", delegate: nil, cancelButtonTitle: "ok")
        alert.show()
    }
    func closeModal(){
        self.dismissViewControllerAnimated(true, completion: nil)
        if let viewController = self.lastViewController as? DetailViewControllerProtocol {
            viewController.reloadCocktail()
        }
    }
}
