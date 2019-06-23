import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user-service';
import { FormGroup, FormControl } from '@angular/forms';
import { UserDto } from 'src/app/model/user-dto';

@Component({
    selector: 'app-edit-profile',
    templateUrl: './edit-profile.component.html',
    styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {

    user: any
    userDto: UserDto

    ediProfileForm = new FormGroup({
        email: new FormControl(),
        firstName: new FormControl(),
        lastName: new FormControl(),
        address: new FormControl(),
        username: new FormControl(),
        password: new FormControl(),
        caption: new FormControl(),
    })

    constructor(private userService: UserService) {
        this.userService.getUserByUsername().subscribe(data => {
            this.user = data;
            console.log(this.user)
            this.intitValues();
        })
    }

    intitValues() {
        this.ediProfileForm.get("firstName").setValue(this.user.firstName)
        this.ediProfileForm.get("lastName").setValue(this.user.lastName)
        this.ediProfileForm.get("address").setValue(this.user.address)
        this.ediProfileForm.get("email").setValue(this.user.email)
        this.ediProfileForm.get("username").setValue(this.user.username)
        this.ediProfileForm.get("caption").setValue(this.user.caption)
        this.ediProfileForm.get("password").setValue(this.user.password)
    }



    ngOnInit() {
    }

    update() {
        this.userDto = new UserDto();

        this.userDto.username = this.userService.username

        if (this.user.firstName != this.ediProfileForm.get("firstName").value)
            this.userDto.firstName = this.ediProfileForm.get("firstName").value;
        else
            this.userDto.firstName = null;
        if (this.user.lastName != this.ediProfileForm.get("lastName").value)
            this.userDto.lastName = this.ediProfileForm.get("lastName").value;
        else
            this.userDto.lastName = null;
        if (this.user.password != this.ediProfileForm.get("password").value)
            this.userDto.password = this.ediProfileForm.get("password").value;
        else
            this.userDto.password = null;
        if (this.user.username != this.ediProfileForm.get("username").value)
            this.userDto.newUsername = this.ediProfileForm.get("username").value;
        else
            this.userDto.newUsername = null;
        if (this.user.address != this.ediProfileForm.get("address").value)
            this.userDto.address = this.ediProfileForm.get("address").value;
        else
            this.userDto.address = null;
        if (this.user.caption != this.ediProfileForm.get("caption").value)
            this.userDto.caption = this.ediProfileForm.get("caption").value;
        else
            this.userDto.caption = null;
        if (this.user.email != this.ediProfileForm.get("email").value)
            this.userDto.email = this.ediProfileForm.get("email").value;
        else
            this.userDto.email = null;

        console.log(this.userDto)

        this.userService.editUser(this.userDto).subscribe( data => {
            console.log(data)
            if (this.userDto.newUsername != null){
                this.userService.username = this.userDto.newUsername
                console.log( this.userService.username)
            }
        })


    }

}