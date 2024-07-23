<template>
    <div>
        <el-container>
            <el-aside width="250px">
                <el-card class="leftNav">
                    <img src="../../../static/cateNav.png" class="leftImg" />
                    <div class="navItem" :class="index == showCategoryIndex ? 'cur' : ''" v-for="(item, index) in navItems" :key="index" @click="showCategory(index)">{{ item }}</div>
                    <div class="navItem" @click="toTop()"><i class="el-icon-arrow-up" style="font-size: 23px;"></i></div>
                </el-card>
            </el-aside>

            <el-main v-loading.fullscreen.lock="loading" element-loading-background="#FFFFFF">
                <div v-show="$store.state.selectBookName != '' && showCategoryIndex == 0">
                    <el-row>
                      <el-col :span="4">
                          <img src="../../../static/background.png" style=" height:100px">
                      </el-col>
                      <el-col :span="12">
                          <h2>为您查到以下书籍</h2>
                      </el-col>
                    </el-row>
                </div>
                
                <el-row>
                    <el-card class="row" v-for="(book, index) in Books[showCategoryIndex].slice((currentPage-1)*pagesize,currentPage*pagesize)" :key="index" v-show="book.state == 'ON_SALE'" :body-style="{ padding: '0px' }">
                        
                        <img class="img" @click="toInfo(book)" :src="'../../../static/booksImage/' + book.imgUrl">
                        <el-link class="name" @click="toInfo(book)" :underline="false">
                            <i class="el-icon-reading readIcon"></i>
                            {{ book.name }}
                        </el-link>
                        <div class="author">{{ book.author }}</div>

                        <div style="position: absolute; bottom: 0;">
                            <el-row type="flex" align="middle">
                                <el-col :span="12" class="price">¥ {{ book.newPrice }}</el-col>
                                <el-col :span="12">
                                    <button class="shop" @click="addToCart(book)">
                                        <i class="el-icon-shopping-bag-1 icon"></i>
                                    </button>
                                </el-col>
                            </el-row>
                            <el-rate class="rate" v-model="book.Commend" disabled></el-rate>
                        </div>
                        
                    </el-card>
                </el-row>

                <el-row class="page">
                    <el-pagination background @current-change="handleCurrentChange" :current-page="currentPage" :page-size="pagesize" :total="Books[showCategoryIndex].length">
                    </el-pagination>
                </el-row>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    
    import 'element-ui/lib/theme-chalk/display.css';
    import axios from "axios";
    export default {
        data() {
            return {
                loading: true,
                scroll: 0, //第一步：定义初始滚动高度
                activeIndex: "1",
                bookPath: 1,
                searchText: "", //搜索关键字
                showCategoryIndex: 0,
                navItems: ["全部书籍", "文学类", "童书", "教育类", "考试类", "人文社科类", "经管综合类", "生活类", "艺术类", "科技类", "其他"],
                Books: [
                    []
                ],
                currentPage: 1,
                pagesize: 20,
                selectBookName :""
            };
        },
        //第二步：mounted中的方法代表dom已经加载完毕
        mounted: function() {
            window.addEventListener('scroll', this.handleScroll);
        },
        computed:{
            booksInfoByName(){
                return this.$store.state.booksInfo
            }
        },
        watch:{
            booksInfoByName(newVal, oldVal){
                this.$set(this.Books, 0, newVal)
            }
        },
        created() {
            this.loading = true
            if(this.$store.state.selectBookName == ''){
                this.getAllBooksInfo()
            }else{
                this.$set(this.Books, 0, this.booksInfoByName)
            }
            console.log(this.Books[0])
            this.loading = false
            this.selectBooksInfoByType(1)
            this.selectBooksInfoByType(2)
            this.selectBooksInfoByType(3)
            this.selectBooksInfoByType(4)
            this.selectBooksInfoByType(5)
            this.selectBooksInfoByType(6)
            this.selectBooksInfoByType(7)
            this.selectBooksInfoByType(8)
            this.selectBooksInfoByType(9)
            this.selectBooksInfoByType(10)
        },
        methods: {
            handleCurrentChange: function(currentPage) {
                this.currentPage = currentPage
            },
            //第三步：用于存放页面函数
            handleScroll() {
                this.scroll = $(window).height() + $(document).scrollTop()
            },
            toTop() {
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            },
            toInfo(e) {
                this.$router.push({
                    path: "/bookInfo",
                    query: {
                        book : e
                    }
                });
            },
            selectBooksInfoByType(index){
                var bookType = index-1
                this.loading = true
                axios({
                    method: 'get',
                    url: "/selectBooksInfoByType",
                    params:{
                        'bookType': bookType
                    }
                }).then(res=>{
                    this.Books[index] = res.data.data;
                    // console.log(this.Books[index])
                    this.loading = false
                    this.$forceUpdate();
                })
            },
            showCategory(index) {
                this.showCategoryIndex = index;
                
            },
            addToCart(e) {
                this.$confirm("确定将此书加入购物车?", "smallFrog", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    })
                    .then(() => {
                        console.log(this.$cookies.get("status"))
                        if(this.$cookies.get('status') == "unlogin"){
                            this.$confirm('您尚未登录！', 'smallFrog', {
                                confirmButtonText: '去登陆',
                                cancelButtonText: '取消',
                                type: 'warning'
                            }).then(() => {
                                this.$router.push({
                                    path: '/login'
                                })
                            })
                        }else{
                            console.log(e.id);
                            axios.post("/joinCart", {
                                customerId: this.$cookies.get('user').id,
                                bookId: e.id,
                                num: 1
                            }).then(res => {
                                console.log("success");
                            });
                            
                            this.$message({
                            type: 'success',
                            message: '成功加入购物车！'
                        });
                    }
                        
                })
                .catch(() => {});
            },
            getAllBooksInfo(){
                axios.post("/getAllBooks").then(res => {
                    this.Books[0] = res.data.data; //获取数据
                    console.log("success");
                    console.log(this.Books[0])
                    // console.log(this.enBooks);
                    this.loading = false
                    this.$forceUpdate();
                })
            },
            test(){
                console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaa")
            }
        },
        //第四步：当再次进入（前进或者后退）时，只触发activated（注：只有在keep-alive加载时调用）
        activated() {
            if (this.scroll > 0) {
                window.scrollTo(0, this.scroll);
                window.addEventListener('scroll', this.handleScroll);
            }
        },
        //第五步：deactivated 页面退出时关闭事件 防止其他页面出现问题
        deactivated() {
            window.removeEventListener('scroll', this.handleScroll);
        }
    };
</script>

<style scoped>
    .title-left {
        line-height: 100px;
        margin-left: 20px;
        font-size: 25px;
        color: #5e52ba;
    }

    .title-right {
        line-height: 100px;
        text-align: end;
        margin-right: 20px;
        color: #3f5576;
    }

    .cateButton {
        border-radius: 25px;
        background-color: #4f6e9d;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
        color: #ffffff;
        margin-left: 20px;
    }

    /* main里的样式 */
    .leftNav {
        top: 50%;
        width: 150px;
        height: 760px;
        margin-top: -350px;
        margin-left: 60px;
        position: fixed;
        border-radius: 20px;
        background-color: #f25353;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
    }

    .leftNav .leftImg {
        width: 100%;
        margin-bottom: 20px;
        color: #ffffff;
        position: relative;
        border-radius: 12px;
    }

    .leftNav .navItem {
        text-align: center;
        height: 60px;
        cursor: pointer;
        color: #ffffff;
        cursor: pointer;
        position: relative;
    }

    .leftNav .navItem:hover {
        transform: scale(1.05);
    }

    .leftNav .navItem.cur::after {
        color: #ffffff;
        content: "";
        width: 5px;
        height: 22px;
        border-radius: 10px 10px 10px 10px;
        position: absolute;
        background-color: currentColor;
        top: 0;
        right: 0;
        margin: auto;
    }

    .row {
        margin-top: 20px;
        margin-bottom: 20px;
        width: 160px;
        height: 400px;
        margin-left: 30px;
        margin-right: 30px;
        display: inline-block;
        position: relative;
    }

    .row .img {
        width: 100%;
        height: 200px;
        /* display: block; */
        cursor: pointer;
        transition: all 0.6s;
    }

    .row .img:hover {
        transform: scale(1.1);
    }

    .row .name {
        padding-top: 14px;
        padding-left: 15px;
        padding-right: 15px;
        font-size: 16px;
    }

    .el-link.el-link--default {
        color: #4f6e9d;
    }

    .el-link.el-link--default:hover {
        color: #7e9dca;
    }

    .row .readIcon {
        text-align: left;
        padding-right: 5px;
    }

    .row .author {
        text-align: left;
        color: #909399;
        padding-top: 14px;
        padding-left: 15px;
        padding-right: 15px;
        font-size: 15px;
    }

    .row .price {
        margin-top: 10px;
        margin-bottom: 10px;
        font-size: 15px;
        padding-left: 15px;
        padding-top: 4px;
    }

    .row .shop {
        margin-top: 10px;
        margin-bottom: 10px;
        border: none;
        outline: none;
        background-color: #ffffff;
        font-size: 20px;
        float: right;
        padding-right: 15px;
    }

    .row .shop .icon {
        color: #4f6e9d;
        cursor: pointer;
        transition: all 0.3s;
    }

    .row .shop .icon:hover {
        transform: scale(1.1);
    }

    .row .rate {
        text-align: center;
        padding: 0 10px 14px 10px;
    }

    .page {
        margin-top: 30px;
        text-align: center;
    }
</style>