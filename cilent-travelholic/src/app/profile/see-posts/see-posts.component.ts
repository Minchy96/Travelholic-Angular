import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user-service';
import { PostService } from 'src/app/services/post-service';
import { FormGroup, FormControl } from '@angular/forms';
import { CommentDto } from 'src/app/model/comment-dto';

@Component({
    selector: 'app-see-posts',
    templateUrl: './see-posts.component.html',
    styleUrls: ['./see-posts.component.scss']
})
export class SeePostsComponent implements OnInit {

    username: String
    posts: any
    commentDto: CommentDto
    deleteSure: number


    commentForm = new FormGroup({
        text: new FormControl(),
    })

    constructor(private userService: UserService, private postService: PostService) {
        this.username = userService.username
        this.getAllPosts()
        this.deleteSure = -1;
    }

    ngOnInit() {
    }

    getAllPosts() {
        this.postService.getUsersPosts(this.username).subscribe(data => {
            this.posts = data
            console.log(this.posts)
        });
    }

    addComment(postId: number) {
        this.commentDto = new CommentDto()
        this.commentDto.username = this.username
        this.commentDto.postId = postId
        this.commentDto.text = this.commentForm.get("text").value;

        console.log(this.commentDto)
        this.postService.saveComment(this.commentDto).subscribe(data => {
            console.log(data)
            this.commentForm.get("text").setValue("");
            this.getAllPosts();
        })
    }

    removeComm(commId: number) {
        this.postService.deleteComment(commId).subscribe(data => {
            console.log(data)
            this.getAllPosts()
        })
    }

    deletePost(postId: number) {
        if (this.deleteSure >= 0) {
            this.postService.deletePost(this.deleteSure).subscribe(data => {
                console.log(data)
                this.getAllPosts()
                this.deleteSure = -1;
            }) 
        } else {
            this.deleteSure = postId;
        }
    }

   

}