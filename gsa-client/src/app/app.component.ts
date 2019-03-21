import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from './services/authentification.service';
import { LocalStorage } from '@ngx-pwa/local-storage';
import { Foo } from './foo';
import { User } from './user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'GSA';

  constructor(private localStorage: LocalStorage, private authentificationService: AuthentificationService) { }

  ngOnInit() {
    this.authentificationService.checkLogin().subscribe(response => {
      if (response.status == 'FAIL') {
        this.localStorage.getItem("user").subscribe(user => {
          if(user != null){
            this.localStorage.clear().subscribe(() => {});
            Foo.subj.next(null);
            // window.location.reload();
          }
        });
      }
      else {
        this.localStorage.getItem("user").subscribe(user => {
          if (user == null) {
            this.localStorage.setItem("user", response.data).subscribe(() => {});
            Foo.subj.next(<User> user);
            // window.location.reload();
          }
        });
      }
    });
  }
}
