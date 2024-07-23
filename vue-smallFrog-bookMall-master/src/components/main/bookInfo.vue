<template>
    <div>
        <div class="pageHeader">
            <el-page-header @back="goBack" content="书籍详情"></el-page-header>
        </div>
        <div v-loading="loading" class="bookInfo">
            <el-card class="box-card leftInfo">
                <img style="height: 310px;" :src="'../../../static/booksImage/' + this.bookInfo.imgUrl">
                <div style="display: flex; margin-top: 20px; align-items: center;">
                    <!-- <div style="color: #606266;">推荐程度：</div> -->
                    <!-- <el-rate style="margin-top: 4px;" v-model="bookInfo.Commend" :colors="colors" disabled></el-rate> -->
                </div>
            </el-card>
            <div class="rightInfo" style="position: relative;">
                <div class="bookName">{{ bookInfo.name }}</div>
                <div class="authorName">{{ bookInfo.author }}</div>
                <p style="font-size: 13px;  color: #606266;">出版时间：{{ bookInfo.date }}</p>
                <div style="display: flex; margin-top: 50px; align-items: center;">
                    <div style="color: #606266;">押金：</div>
                    <div class="bookPrice">¥ {{ bookInfo.newPrice }}</div>
                    
                </div>
                <div>
                    <p style="font-size: 13px;  color: #606266;">原价：¥ {{bookInfo.oldPrice}}</p>
                </div>
                <div style="display: flex; margin-top: 50px; align-items: flex-end;">
                    <div style="color: #606266;">书架数量：</div>
                    <div>{{ bookInfo.inventory }}</div>
                </div>
                <div>
                    <p style="font-size: 13px;  color: #606266;">已借：{{bookInfo.sold}}</p>
                </div>
                <div >
                    <div style="display: flex; align-items: center;">
                        <div style="color: #606266;">数量：</div>
                        <el-input-number v-model="num" @change="handleChange" :min="1" :max="bookInfo.Book_Num"></el-input-number>
                    </div>
                    <div style="display: flex; margin-top: 40px;">
                        <el-button class="buyButton1" @click="addToCart(bookInfo)">加入书籍管理</el-button>
                        <el-button class="buyButton2" style="margin-left: 30px;" @click="toSettle(bookInfo)">立即借阅</el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    var address = "http://localhost:8080"
    export default {
        data() {
            return {
                loading: true,
                bookInfo: [],
                num: 1,
                cart: [{
                    customerId:"",
                    bookId:"",
                    num:"",
                    imgUrl:"",
                    newPrice:"",
                    bookName:"",
                }]
            }
        },
        created() {
            this.getInfo();
        },
        methods: {
            getInfo() {
                this.bookInfo = this.$route.query.book
                console.log(this.bookInfo)
                axios.post("/bookInfo", {
                    id: this.bookInfo.id
                }).then(res => {
                    this.bookInfo = res.data.data; //获取数据  
                    console.log('success');
                    console.log(this.bookInfo);
                });
                this.loading = false;
            },
            handleChange(value) {
                // console.log(value);
            },
            goBack() {
                this.$router.go(-1);
            },
            addToCart(e) {
                if (this.$cookies.get('status') == 'logined') {
                    axios.post("/joinCart", {
                            customerId: this.$cookies.get('user').id,
                            bookId: e.id,
                            num: this.num
                    }).then(res => {
                        console.log("")
                        console.log("success");
                    });
                    this.$message({
                        type: 'success',
                        message: '成功加入书籍管理！'
                    })
                } else {
                    this.$confirm('您尚未登录！', 'smallFrog', {
                        confirmButtonText: '去登陆',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.$router.push({
                            path: '/login'
                        })
                    });
                }
            },
            setCart() {
                this.cart[0].customerId = this.$cookies.get('user').id;
                this.cart[0].bookId = this.bookInfo.id;
                this.cart[0].num = this.num;
                this.cart[0].imgUrl = this.bookInfo.imgUrl
                this.cart[0].newPrice = this.bookInfo.newPrice
                this.cart[0].bookName = this.bookInfo.name
            },
            toSettle() {
                if (this.$cookies.get('status') == 'logined') {
                    this.setCart();
                    console.log(this.cart)
                    var data = this.cart
                    this.$router.push({
                        path: "/shopping/settle",
                        query:{
                            cart : this.cart
                        }
                    });
                    console.log("query:  ",this.$route.query)
                } else {
                    this.$confirm('您尚未登录！', 'smallFrog', {
                        confirmButtonText: '去登陆',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.$router.push({
                            path: '/login'
                        })
                    });
                }   
            }
        }
    }
</script>

<style>
    .bookInfo {
        justify-content: center;
        margin-top: 50px;
        margin-bottom: 80px;
        display: flex;
    }

    .leftInfo {
        margin-left: 0;
        height: 400px;
    }

    .rightInfo {
        width: 400px;
        height: 400px;
        margin-left: 150px;
        padding-left: 30px;
    }

    .bookName {
        font-size: 23px;
        font-weight: 600;
        
    }

    .authorName {
        margin-top: 20px;
        color: #909399;
    }

    .bookPrice {
        color: #c00000;
        font-size: 23px;
        font-weight: 600;
    }

    .buyButton1 {
        width: 150px;
        background-color: #4F6E9D;
        color: #FFFFFF;
        outline: none;
        border-color: #4F6E9D;
        border-radius: 0;
    }

    .buyButton1:focus,
    .buyButton1:hover {
        background-color: #7e9dca;
        border-color: #7e9dca;
        color: #FFFFFF;
        outline: none;
    }

    .buyButton2 {
        width: 150px;
        outline: none;
        border-radius: 0;
        color: #4f6e9d;
    }

    .buyButton2:focus,
    .buyButton2:hover {
        background-color: #7e9dca;
        border-color: #7e9dca;
        color: #FFFFFF;
        outline: none;
    }
</style>