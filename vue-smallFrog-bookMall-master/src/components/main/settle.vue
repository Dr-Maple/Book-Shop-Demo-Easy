<template>
    <div>
        <div class="settle">
            <div class="viewBox">
                <div class="title">
                    <div class="slogan">填写并核对借阅信息</div>
                    <el-button class="submit" type="danger" @click="toPay()">提交信息</el-button>
                </div>
                <el-divider></el-divider>

                <el-row type="flex" align="middle">
                    <el-col :span="4">
                        <el-button icon="el-icon-caret-left" class="back" @click="goBack()">上一步</el-button>
                    </el-col>
                    <el-col :span="20">
                        <el-steps :active="1" finish-status="success" simple>
                            <el-step title="信息" icon="el-icon-s-goods" @click="toCart()"></el-step>
                            <el-step title="借阅" icon="el-icon-s-claim"></el-step>
                            <el-step title="押金" icon="el-icon-s-finance"></el-step>
                            <el-step title="取书" icon="el-icon-s-home"></el-step>
                            <el-step title="归还" icon="el-icon-success"></el-step>
                        </el-steps>
                    </el-col>
                </el-row>

                <el-divider></el-divider>

                <div class="commodity">
                    <el-row type="flex" align="middle">
                        <el-col :span="3" class="cTitle">书籍封面</el-col>
                        <el-col :span="9" class="cTitle">书籍名称</el-col>
                        <el-col :span="6" class="cTitle">押金</el-col>
                        <el-col :span="6" class="cTitle">数量</el-col>
                    </el-row>
                </div>

                <div v-if="this.cart[0]">
                    <div class="commodity" v-for="(book, index) in cart" :key="index">
                        <el-row type="flex" align="middle">
                            <el-col :span="3">
                                <img class="bookImg" :src="'../../../static/booksImage/' + book.imgUrl">
                            </el-col>
                            <el-col :span="9">{{ book.bookName }}</el-col>
                            <el-col :span="6">{{ book.newPrice }}</el-col>
                            <el-col :span="6">{{ book.num }}</el-col>
                        </el-row>
                    </div>
                    <el-divider></el-divider>

                    <div class="orderInfo">
                        <div class="left">
                            <div class="infoTitle">借阅信息</div>
                            <div class="info">名字：{{ defaultAddress.receiver }}</div>
                            <div class="info">电话：{{ defaultAddress.tel }}</div>
                            <div class="info">地址：{{ defaultAddress.address }}</div>
                        </div>
                        <el-button class="modify" type="info" @click="handleEdit()" plain>修改</el-button>
                    </div>
                    <el-divider></el-divider>

                    <div class="settleFooter">
                        <div class="leftImg"><img class="settleImg" src="../../../static/orderConfirm.png"></div>
                        <div class="total">
                            <div class="postage"> 借阅卡：- {{memberPrice}}元</div>
                            <div class="postage">邮费：{{ postage }} 元</div>
                            <div class="postage">押金：{{ totalPrice }} 元</div>
                            <div class="payPrice">应付：{{ totalPrice + postage -  memberPrice}} 元</div>
                        </div>
                    </div>
                </div>

                <div v-if="!this.cart[0]">
                    <el-row type="flex" align="middle">
                        <el-col :span="24" style="text-align: center;">暂无书籍</el-col>
                    </el-row>
                    <el-divider></el-divider>

                    <div class="orderInfo">
                        <div class="left">
                            <div class="infoTitle">借阅信息：无</div>
                        </div>
                        <el-button class="modify" type="info" @click="modify()" plain>修改</el-button>
                    </div>
                    <el-divider></el-divider>

                    <div class="settleFooter">
                        <div class="leftImg"><img class="settleImg" src="../../../static/orderConfirm.png"></div>
                        <div class="total">
                            <div class="postage">押金：0 元</div>
                            <div class="postage">邮费：0 元</div>
                            <div class="payPrice">应付：0 元</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 收货信息编辑弹出框 -->
            <el-dialog title="修改信息" :visible.sync="editVisible" width="35%">
                <el-form label-position="right" :rules="rules" ref="defaultAddress" :model="defaultAddress" label-width="100px">
                    <el-form-item label="请选择：">
                        <el-select v-model="selectAddress" placeholder="请选择" @change="addressChange" style="width:450px;" clearable >
                          <el-option v-for="item in userAddressInfo" :label="item.receiver + ' ' + item.tel + ' ' + item.address" :value="item.id" :key="item.id">
                          </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="名字：" prop="receiver" >
                        <el-input v-model="defaultAddress.receiver" style="width:450px;"></el-input>
                    </el-form-item>
                    <el-form-item label="电话：" prop="tel">
                        <el-input v-model="defaultAddress.tel" style="width:450px;"></el-input>
                    </el-form-item>
                    <el-form-item label="地址：" prop="address">
                        <el-input type="textarea" v-model="defaultAddress.address" style="width:450px;"></el-input>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        inject: ['reload'],
        data() {
            // <!--验证手机号是否合法-->
            let checkTel = (rule, value, callback) => {
                if (!this.checkMobile(value)) {
                    callback(new Error('请输入正确的手机号'))
                } else {
                    callback() 
                }
            }
            return {
                input: '',
                defaultAddress:{},
                userAddressInfo: {},
                selectAddress: {},
                postage: 0,
                cart: [],
                count: 0,
                totalPrice: 0,
                memberPrice: 0,
                editVisible: false,
                rules:{
                    tel:[{
                        validator: checkTel,
                        trigger: 'change'
                    }],
                },
                orderState: 1,
                orders:{
                    bookId: "",
                    customerId: "",
                    address: "",
                    receiver: "",
                    tel: "",
                    num: "",
                    singleprice :"",
                    totalPrice: "",
                    state: "",
                    data: "",
                    endData: "",
                },
                ordersList:[],
                allAddress: [{
                    lable:"",
                    value:""
                }]

            }
        },
        created() {
            console.log(this.$cookies.get('user'))
            var user_ID = this.$cookies.get('user').id;
            let count = 0;
            let totalPrice = 0;
            

            this.cart = this.$route.query.cart;
            // console.log(this.cart[1])
            // console.log(this.cart)

            for (let i = 0; i < this.cart.length; i++) {
                count += parseFloat(this.cart[i].num);
                totalPrice += parseFloat(this.cart[i].newPrice * this.cart[i].num);
            }
            this.count = count;
            this.totalPrice = totalPrice;
            if(this.$cookies.get('user').member == 'true'){
                this.memberPrice = totalPrice * 0.05;
            }

            axios.post("/getCustomerAddress", {
                id: user_ID
            }).then(res => {
                this.userAddressInfo = res.data.data; //获取数据
                console.log(this.userAddressInfo)
                for(let address in this.userAddressInfo){
                    if(this.userAddressInfo[address].flagDefault){
                        this.defaultAddress = this.userAddressInfo[address];
                        this.selectAddress = this.defaultAddress.receiver + ' ' + this.defaultAddress.tel + ' '+ this.defaultAddress.address;
                        // console.log(this.userAddressInfo[address])
                    }
                };
                // console.log(this.defaultAddress)
                console.log("success");
            });
        },
        methods: {
            // 验证手机号
            checkMobile (str) {
                let re = /^1\d{10}$/
                if (re.test(str)) {
                    return true
                } else {
                    return false
                }
            },
            addressChange(){
                console.log(this.selectAddress)
                for(let index in this.userAddressInfo){
                    if(this.userAddressInfo[index].id == this.selectAddress){
                        this.defaultAddress = this.userAddressInfo[index]
                    }
                }
            },
            goBack() {
                this.$router.go(-1);
            },
            toCart() {
                this.$router.push({
                    path: '/shopping/cart'
                })
            },
            // 编辑操作
            handleEdit() {
                this.editVisible = true;
            },
            modify(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log('修改成功！');
                        this.$message({
                            showClose: true,
                            message: '修改成功！',
                            type: 'success',
                            center: true
                        });
                        this.editVisible = false;
                    } else {
                        console.log('error!');
                        return false;
                    }
                });
            },
            toPay() {
                if (!this.cart[0]) {
                    this.$message({
                        showClose: true,
                        message: '无订单信息！',
                        type: 'warning',
                        center: true
                    });
                } else {
                    var that = this
                    for(let i=0; i<that.cart.length; i++){
                        that.orders.bookId = that.cart[i].bookId
                        that.orders.customerId = that.$cookies.get('user').id
                        that.orders.num = that.cart[i].num
                        that.orders.singlePrice = that.cart[i].newPrice
                        that.orders.totalPrice = that.cart[i].num * that.cart[i].newPrice
                        if(this.$cookies.get('user').member == "true"){
                            that.orders.totalPrice = that.orders.totalPrice * 0.95
                        }
                        that.orders.receiver = that.defaultAddress.receiver
                        that.orders.tel = that.defaultAddress.tel
                        that.orders.address = that.defaultAddress.address
                        that.orders.data = new Date()
                        var order = Object.assign({}, that.orders)
                        // console.log(order)
                        that.ordersList[i] = order
                    }
                    this.$confirm('是否确认支付', '确认信息', {
                        distinguishCancelAndClose: true,
                        confirmButtonText: '支付',
                        cancelButtonText: '放弃'
                    }).then(() => {
                        this.$message({
                        type: 'success',
                        message: '支付成功'
                        });
                        for(let i in this.ordersList){
                            this.ordersList[i].state = 2
                            console.log(this.ordersList[i].state)
                        }
                        axios.post("/createOrders",this.ordersList).then(res=>{
                            axios.post("/deleteCartInfo", this.cart).then(res=>{

                            })
                        })
                        this.$router.push({
                        path: "/shopping/cart",
                        query: {
                            
                        }
                    });
                    })
                    .catch(action => {
                        this.$message({
                        type: 'info',
                        message: action === 'cancel'
                            ? '放弃保存并离开页面'
                            : '停留在当前页面'
                        })
                        for(let i in this.ordersList){
                            this.ordersList[i].state = 1
                        }
                        axios.post("/createOrders",this.ordersList).then(res=>{
                            console.log("test")
                        })
                        this.$router.push({
                        path: "/shopping/cart",
                        query: {
                            
                        }
                    });
                    });
                    
                }
            }
        }
    }
</script>

<style scoped>
    .settle {
        width: 80%;
        margin: 0 auto;
        /* background-color: #f5f5f5; */
    }

    .settle .viewBox {
        width: 80%;
        margin: 0 auto;
    }

    .settle .viewBox .title {
        height: 100px;
        display: flex;
        align-items: center;
    }

    .settle .viewBox .title .slogan {
        width: 90%;
        height: 100px;
        line-height: 100px;
        font-size: 24px;
        color: #303133;
        float: left;
    }

    .settle .viewBox .title .submit {
        width: 150px;
        height: 50px;
        border-radius: 0;
        font-size: 16px;
    }

    .settle .viewBox .back {
        width: 100px;
        border-radius: 0;
    }

    .settle .viewBox .commodity {
        width: 80%;
        margin: 0 auto;
        text-align: center;
        margin-bottom: 10px;
    }

    .settle .viewBox .commodity .cTitle {
        height: 60px;
        line-height: 40px;
        font-weight: bold;
    }

    .settle .viewBox .commodity .bookImg {
        width: 60px;
    }

    .settle .viewBox .orderInfo {
        height: 180px;
        display: flex;
        align-items: center;
    }

    .settle .viewBox .orderInfo .left {
        width: 90%;
        height: 180px;
        line-height: 180px;
        font-size: 18px;
        color: #606266;
    }

    .settle .viewBox .orderInfo .left .infoTitle {
        width: 90%;
        height: 30px;
        line-height: 30px;
        font-size: 22px;
        color: #303133;
        margin-bottom: 30px;
    }

    .settle .viewBox .orderInfo .left .info {
        width: 90%;
        height: 40px;
        line-height: 40px;
        font-size: 17px;
        color: #606266;
    }

    .settle .viewBox .orderInfo .modify {
        width: 100px;
        height: 40px;
        border-radius: 0;
        font-size: 16px;
    }

    .settle .viewBox .settleFooter {
        width: 100%;
        display: flex;
    }

    .settle .viewBox .settleFooter .leftImg {
        width: 80%;
        /* float: left; */
    }

    .settle .viewBox .settleFooter .settleImg {
        width: 150px;
        float: left;
    }

    .settle .viewBox .settleFooter .total {
        width: 20%;
        height: 150px;
        align-items: center;
    }

    .settle .viewBox .settleFooter .total .postage {
        margin-bottom: 10px;
        margin-right: 10px;
        /* float: right; */
        text-align: right;
    }

    .settle .viewBox .settleFooter .total .payPrice {
        margin-top: 30px;
        font-size: 20px;
        color: #4f6e9d;
        float: right;
    }
</style>