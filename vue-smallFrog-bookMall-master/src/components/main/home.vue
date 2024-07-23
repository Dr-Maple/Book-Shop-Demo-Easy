<template>
    

   

    <div class="container">


        <div class="top">
			<h3></h3>
			<div class="author"></div>
		</div>
        <div class="dajianshi">
                <div class="leftDoor"><img src="static\booksImage\t018cb991f25495d910.jpg" width=200px height=200px /></div>
                <div class="rightDoor"><img src="static\booksImage\微信图片_20240530140449.jpg" width=200px height=200px /></div>
                <div class="middle"><p><br/>#队员:侯德伟 史轩轶  张斯哲 杨佳莹 <br/><br/>#B/S架构--图书馆管理系统--第七组
                <br/><br/>#技术支持：SpringBoot+vue+MyBatisPlus<br/> Elementplus+Axios+SpringSecurity+JWT<br/>Redis+Pinia+Nginx+Mysql+LinuxCentOs7
               </p>
                 
                
                </div>
		    </div>
        
        
        <el-container>
            <div style="display: flex; margin-top: 40px;">
                <div>
                    <h1 class="homeTitle">Library Manage</h1>
                    
                    <p class="content"><span class="text"><font color="red" class="as">宝剑</font>锋从磨砺出<font color="blue" class="as">&nbsp;梅花</font>香自苦寒来</span></p>
                    
                    <el-button class="startBtn" @click="toCate()">Manage Now!</el-button>
                </div>
                
               
            </div>
        </el-container>
        

        <div class="recommend">
            <!-- <h1 class="recommendTitle">为您推荐</h1> -->
            <div class="recLine" data-wow-duration="2s" v-for="(books, index) in transRecBooks" :key="index">
                <div v-for="(book, index) in books" :key="index">
                    <el-card slot="reference" class="wow slideInUp card" :body-style="{ padding: '0px' }">
                        <img class="img" @click="toInfo(book)" :src="'http://www.xiaoqw.online/smallFrog-bookstore/img/' + book.img">
                        
                        <div class="mask">
                            <el-link class="name" @click="toInfo(book)" :underline="false">
                                <i class="el-icon-reading readIcon"></i>
                                {{ book.Name }}
                            </el-link>
                            <el-rate class="rate" v-model="book.Commend" disabled></el-rate>
                        </div>
                        
                    </el-card>
                </div>
            </div>
        </div>
        
    </div>
</template>

<script>
    import axios from "axios";
    import animate from 'animate.css'
    // import {WOW} from 'wowjs';

    export default {
        
        data() {
            return {
                recBooks: {},
                transRecBooks: [],
            }
        },
        created() {
            // axios.post("http://localhost:8080/getAllBooks").then(res => {
            //     //这里是ES6的写法，get请求的地址
            //     this.recBooks = res.data; //获取数据
            //     console.log("success");
            //     console.log(this.recBooks);
            //     this.transRec();
            // })
        },
        methods: {
            transRec() {
                var Arr = [];
                for (var i = 0, idx = -1; i < this.recBooks.length; i++) {
                    i % 4 == 0 && idx++;
                    if (Object.prototype.toString.call(Arr[idx]) != "[object Array]")
                        Arr[idx] = [];
                    Arr[idx].push(this.recBooks[i]);
                }
                this.transRecBooks = Arr;
            },
            toInfo(e) {
                this.$router.push({
                    path: "/bookInfo",
                    query: {
                        ID: e.ID
                    }
                });
            },
            toCate(e) {
                this.$router.push({
                    path: "/category",
                });
            }
        }
    }
</script>

<style scoped>
    .homeTitle {
        width: 400px;
        color: #333333;
        font-size: 40px;
        font-weight: 600;
        text-align: left;
        margin-left: 50px;
    }

    .indexImg {
        width: 500px;
        margin-left: 200px;
    }

    .content {
        width: 400px;
        color: #333333;
        font-size: 18px;
        line-height: 35px;
        text-align: left;
        margin-left: 100px;
    }





    

    .startBtn {
        width: 180px;
        height: 50px;
        margin-left: 100px;
        margin-top: 40px;
        border-radius: 10%;
        font-size: 16px;
        border: 0;
        outline: 0;
        background-color: #4F6E9D;
        color: #FFFFFF;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    }

    .startBtn:focus,
    .startBtn:hover {
        color: #FFFFFF;
        border-color: #7E9DCA;
        background-color: #7E9DCA;
    }

    .recommend {
        margin-top: 30px;
        padding-top: 30px;
        padding-bottom: 50px;
        /* background-color: #f8f9fb; */
        /* background-image: linear-gradient(#FFFFFF, #F2F2F7); */
    }

    .recommend .recommendTitle {
        color: #333333;
        text-align: center;
        font-size: 30px;
        font-weight: 600;
        margin-bottom: 50px;
    }

    .recommend .recLine {
        justify-content: center;
        display: flex;
    }

    .recommend .recLine .card {
        width: 200px;
        margin-top: 20px;
        margin-bottom: 20px;
        margin-left: 40px;
        margin-right: 40px;
        /* border-radius: 15px; */
        position: relative;
        text-align: justify;
    }

    .recommend .recLine .card .img {
        width: 80%;
        display: block;
        cursor: pointer;
        margin: 0 auto;
        padding-top: 20px;
    }

    .recommend .recLine .card .mask {
        width: 100%;
        margin-top: 20px;
        background-color: #f8fafc;
    }

    .recommend .recLine .card .mask .name {
        padding-top: 14px;
        padding-left: 20px;
        padding-right: 20px;
        padding-bottom: 14px;
        font-size: 16px;
    }

    .recommend .recLine .card .mask .rate {
        text-align: center;
        padding: 0 10px 14px 10px;
    }

    .el-link.el-link--default {
        color: #4f6e9d;
    }

    .el-link.el-link--default:hover {
        color: #7e9dca;
    }



    .container {
		width: 1000px;
		height: 600px;
		margin: 50px auto;
		border: 1px solid rgba(73, 50, 189, 0.887);
		position: relative;
        
        
	}

	.top {
		margin: 0 auto 0px;
		padding: 10px 0;
		background: rgb(46, 55, 139);
		color: #fff;
        
	}

	.dajianshi {
		width: 400px;
		height: 200px;
		margin-top: 10%;
        margin-right: 13%;
		border: 1px solid #666;
		perspective: 500px;
		position: relative;
		display: flex;
        float: right;

	}

	.leftDoor,
	.rightDoor {
		width: 50%;
		height: 100%;
		background-color:orange;
      
        transition: 1.2s;
		z-index: 2;
		display: flex;
		justify-content: center;
		align-items: center;
		border: 1px solid #cde;

	}
    .leftDoor,
    .rightDoor img{
        border: 5px solid #333333;
    }
    
	.leftDoor {
		transform-origin: left;
        transform: rotateY(-20deg);
        
	}

	.rightDoor {
		transform-origin: right;
	}

	.dajianshi:hover .leftDoor {
		transform: rotateY(-130deg);
	}

	.dajianshi:hover .rightDoor {
		transform: rotateY(130deg);
	}

	.middle {
		position: absolute;
		z-index: 1;
		width: 100%;
		height: 100%;
		background-color: rgba(61, 84, 147, 0.285);
		display: flex;
		justify-content: center;
		align-items: center;
	}




</style>