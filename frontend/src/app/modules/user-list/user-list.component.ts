import {Component, OnInit} from "@angular/core";
import {UserService} from "../../service/user.service";
import {Pageable} from "../../model/Pageable";
import {User} from "../../model/User";
import {Router} from "@angular/router";
import {AppContext} from "../../util/AppContext";


@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

    pageable: Pageable = new Pageable(0, 3)
    userList: Array<User> = [];

    constructor(
        private userService: UserService,
        private router: Router,
        public appContext: AppContext
    ) {
    }

    ngOnInit() {
        this.getUserList()
    }

    getUserList() {
        this.userService.getUserList(this.pageable?.page).subscribe(
            response => {
                this.userList = response.content;
            }
        );
    }

    selectUser(user: User) {
        console.log("User selected ->", user)
        this.router.navigateByUrl('/user-profile', {state: {user: user}})
    }


}
