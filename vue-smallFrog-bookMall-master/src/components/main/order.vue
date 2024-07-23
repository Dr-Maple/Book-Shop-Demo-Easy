<template>
    <div>
        <el-container>
            <el-header>
                <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-menu-item index="1">
                        <i class="el-icon-s-order"></i>全部借阅</el-menu-item>
                    <el-menu-item index="2">
                        <i class="el-icon-wallet"></i>待借阅</el-menu-item>
                    <el-menu-item index="3">
                        <i class="el-icon-position"></i>借阅中</el-menu-item>
                    <el-menu-item index="4">
                        <i class="el-icon-sold-out"></i>待归还</el-menu-item>
                </el-menu>
            </el-header>
            <el-main v-loading.fullscreen.lock="loading" element-loading-background="#FFFFFF">
                <div v-show="orders[showCategoryIndex] == ''" style="text-align: center;">
                    <h4>您还没有相关的书籍！！！</h4>
                    <img src="../../../static/shoppingCart.jpg" style="height:400px;">
                </div>
                
                <el-card class="row" v-for="(order, index) in orders[showCategoryIndex].slice((currentPage-1)*pagesize,currentPage*pagesize)" :key="index" :body-style="{ padding: '0px' }">
                    <el-row type="flex" align="middle" :gutter="30">
                        <el-col :span="5">
                            <div class="img">
                                <el-image fit="fill" :src="'../../../static/booksImage/' + order.bookImgUrl" style="height: 150px;"></el-image>
                            </div>
                        </el-col>
                        
                        <el-col>
                            <el-row type="flex" :gutter="20" align="middle">
                                <el-col :span="8">
                                    <i class="el-icon-reading readIcon">    {{ order.bookName }}</i>
                                </el-col>
                                <el-col :span="10">
                                    <p>作者：{{order.bookAuthor}}</p>
                                </el-col>
                                <el-col :span="4" :offset="2">
                                    <el-button type="primary" v-show="order.state == '待付款'" @click="toPay(order)">去付款</el-button>
                                    <el-button type="primary" v-show="order.state == '待发货'">提醒借阅</el-button>
                                    <el-button type="primary" v-show="order.state == '待收货'" @click="confirm(order)">确认归还</el-button>
                                </el-col>
                            </el-row>
                            <el-row type="flex" :gutter="12">
                                <el-col :span="2"><p class="address" style="color: rgb(13, 13, 247);">书籍信息：</p></el-col>
                                <el-col :span="4"><p class="address">数量：{{ order.num }}</p></el-col>
                                <el-col :span="4"><p class="address">定金：{{order.singlePrice}} 元</p></el-col>
                                <el-col :span="10"><p class="address">书价：{{order.totalPrice}} 元</p></el-col>
                                <el-col :span="4"><p class="address" style="color: green;">借阅卡：{{(order.singlePrice*order.num-order.totalPrice).toFixed(2)}} 元</p></el-col>
                            </el-row>
                            <el-row type="flex" :gutter="12">
                                <el-col :span="2"><p class="address" style="color: rgb(13, 13, 247);">收货信息：</p></el-col>
                                <el-col :span="4"><p class="address">借阅人：{{order.receiver}}</p></el-col>
                                <el-col :span="4"><p class="address">电话：{{order.tel}}</p></el-col>
                                <el-col :span="10"><p class="address">地址：{{order.address}}</p></el-col>
                                <el-col :span="4"></el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="6" :offset="18"><p class="address">下单时间：{{order.date}}</p></el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="6" :offset="18"><p class="address">付款时间：{{order.endDate}}</p></el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                </el-card>
                

                <el-row class="page">
                    <el-pagination background @current-change="handleCurrentChange" :current-page="currentPage" :page-size="pagesize" :total="orders[showCategoryIndex].length">
                    </el-pagination>
                </el-row>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    import 'element-ui/lib/theme-chalk/display.css';
    import axios from "axios";
import moment from 'moment';
    export default {
        data(){
            return{
                activeIndex: '1',
                loading: true,
                orders:[
                    []
                ],
                currentPage: 1,
                pagesize: 20,
                showCategoryIndex: 0,
                bookPath: 1,
                customerId:"",
            }
        },
        mounted: function() {
            
            window.addEventListener('scroll', this.handleScroll);
        },
        created(){
            this.customerId = this.$cookies.get('user').id;
            axios.post("/getAllOrders", {
                customerId: this.customerId
            }).then(res=>{
                this.orders[0] = res.data.data
                console.log(res.data.data)
                this.loading = false
                this.$forceUpdate()
            })
            this.getOrdersByState(1)
            this.getOrdersByState(2)
            this.getOrdersByState(3)
        },
        methods: {
            handleCurrentChange: function(currentPage) {
                this.currentPage = currentPage
            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
                this.showCategoryIndex = key-1;
            },
            getOrdersByState(e){
                this.loading = true
                console.log(e),
                axios({
                    method:"get",
                    url:"/getOrdersInfoByStateAndCustomerId",
                    params:{
                        customerId: this.customerId,
                    state: e
                    }
                }).then(res=>{
                    this.orders[e] = res.data.data
                    console.log(this.orders[e])
                    this.loading = false
                })
            },
            showCategory(index) {
                this.showCategoryIndex = index;
            },
            toPay(order){
                this.$confirm('确认付款?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {
                        order.state = "待出书"
                        order.endDate = moment(new Date()).format("YYYY-MM-DD hh:mm:ss")
                        this.loading = true
                        axios.post("/editOrderInfo",order).then(res=>{
                            this.$message({
                                type: 'success',
                                message: '借阅成功!'
                            });
                            this.loading = false
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消支付'
                        });          
                    });
            },
            confirm(order){
                this.$confirm("是否归还?","提示",{
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(()=>{
                    order.state = "已完成"
                    this.loading = true
                    axios.post("/editOrderInfo", order).then(res=>{
                        this.$message({
                            type: 'success',
                            message: '归还成功!'
                        });
                        this.loading = false
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消归还'
                    }); 
                });
            }
        }
    }
</script>

<style>
    .row {
        margin-top: 10px;
        margin-bottom: 10px;
        width: 90%;
        margin-left: 30px;
        margin-right: 30px;
        display: inline-block;
        position: relative;
        padding-top: 10px;
        padding-bottom: 10px;
        padding-left: 15px;
        padding-right: 15px;
    }
    .address{
        font-size: 10px;
        
    }
</style>
