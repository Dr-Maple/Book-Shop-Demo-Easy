<template>
    <div>
        <el-row type="flex" align="middle">
            <el-col :span="4">
                <img class="bannerLogo" src="../../../static/shoppingCart.jpg">
            </el-col>
            <el-col :span="20">
                <div class="banner">
                    <div class="prompt_a">借阅管理</div>
                    <div class="prompt_b">温馨提示：书籍是否借阅成功，以最终下单为准哦，请尽快结算</div>
                </div>
            </el-col>
        </el-row>
        <div class="cart_line"></div>

        <div v-if="this.cart[0]">
            <el-table :data="cart" class="list" :header-cell-style="{background:'#eef1f6',color:'#606266'}" @selection-change="handleSelectionChange" >
              <el-table-column label="全选" type="selection"></el-table-column>
              <el-table-column header-align="center" align="center" prop="imgUrl" label="书籍封面">
                <template slot-scope="scope">
                    <img style="height: 80px;" :src="'../../../static/booksImage/'+ scope.row.imgUrl">
                </template>    
             </el-table-column>
              <el-table-column header-align="center" align="center" prop="bookName" label="商品名称" v-model="cart.bookName"> </el-table-column>
              <el-table-column header-align="center" align="center" prop="newPrice" label="单价" v-model="cart.newPrice"> </el-table-column>
              <el-table-column header-align="center" align="center" prop="num" label="数量" v-model="cart.num">
                  <template slot-scope="scope">
                      <el-input-number v-model="scope.row.num" :min="1" size="small" @change="numChange(scope.row)"></el-input-number>
                  </template>
             </el-table-column>
              <el-table-column header-align="center" align="center" prop="" label="小计"> 
                  <template slot-scope="scope">
                      {{ scope.row.newPrice * scope.row.num }}元
                  </template>
              </el-table-column>
              <el-table-column header-align="center" align="center" prop="" label="操作">
                  <template slot-scope="scope">
                    <el-button type="danger" icon="el-icon-delete" @click="cartDelete(scope.row)" circle></el-button>
                  </template>
                   
            </el-table-column>

            </el-table>
            <div class="summary">
                <el-row type="flex" align="middle">
                    <el-col :span="6">
                        <div class="number">共 {{ count }} 件商品</div>
                    </el-col>
                    <el-col :span="14">
                        <div class="price">合计（不含运费）：{{ totalPrice }} 元</div>
                    </el-col>
                    <el-col :span="4" class="settlement">
                        <div @click="toSettle()">结算</div>
                    </el-col>
                </el-row>
            </div>
        </div>

        <div v-if="!this.cart[0]">
            <div class="list">
                <el-row type="flex" align="middle">
                    <el-col :span="3" class="title">书籍封面</el-col>
                    <el-col :span="9" class="title">书籍名称</el-col>
                    <el-col :span="3" class="title">押金</el-col>
                    <el-col :span="3" class="title">数量</el-col>
                    <el-col :span="3" class="title">小计</el-col>
                    <el-col :span="3" class="title">操作</el-col>
                </el-row>

                <el-row type="flex" align="middle">
                    <el-col :span="24" class="title">暂无书籍噢，赶紧去借阅吧！</el-col>
                </el-row>
            </div>

            <div class="summary">
                <el-row type="flex" align="middle">
                    <el-col :span="6">
                        <div class="number">共 0 件书籍</div>
                    </el-col>
                    <el-col :span="14">
                        <div class="price">合计（不含运费）：0 元</div>
                    </el-col>
                    <el-col :span="4" class="settlement">
                        <div @click="toSettle()">结算</div>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    var address = "http://localhost:8080";
    export default {
        inject: ['reload'],
        data() {
            return {
                cart: [],
                count: 0,
                num: 0,
                totalPrice: 0,
                multipleSelection: []
            }
        },
        created() {
            var user_ID = this.$cookies.get('user').id;
            var num = 0;
            var totalPrice = 0;

            axios.post('/Cart', {
                'data': user_ID
            }).then(res => {
                this.cart = res.data.data; //获取数据
                console.log("success");
                console.log(this.cart);

                for (let i = 0; i < this.cart.length; i++) {
                    num += parseFloat(this.cart[i].num);
                    totalPrice += parseFloat(this.cart[i].newPrice * this.cart[i].num);
                }
                this.num = num;
                this.count = this.cart.length;
                this.totalPrice = totalPrice;
            });
        },
        methods: {
            cartDelete(e) {
                console.log(e)
                axios.post("/deleteCartBook", {
                    "cartId": e.id
                }).then(response => {
                    console.log('删除成功');
                    this.$message({
                        showClose: true,
                        message: '删除成功！',
                        type: 'success',
                        center: true
                    });
                    this.reload();
                });
            },
            toSettle() {
                if (!this.cart[0]) {
                    this.$message({
                        showClose: true,
                        message: '借阅管理中还没有书籍噢！',
                        type: 'warning',
                        center: true
                    });
                }
                else {
                    this.$router.push({
                        path: "/shopping/settle",
                        query: {
                            cart: this.multipleSelection
                        }
                    });
                }
            },
            numChange(cartBook){
                console.log(cartBook)
                axios.post("/updateBookNum",{
                    id : cartBook.id,
                    customerId : cartBook.customerId,
                    bookId: cartBook.bookId,
                    num: cartBook.num,
                }).then(res=>{
                    console.log("updateBookNum success!!!")
                })
            },
            handleSelectionChange(val){
                this.multipleSelection = val
                console.log(this.multipleSelection)
            }
        }
    }
</script>

<style scoped>
    .bannerLogo {
        width: 60%;
        margin-left: 100px;
        margin-top: -25px;
    }

    .banner {
        width: 100%;
        height: 80px;
        display: flex;
        margin-bottom: 20px;
    }

    .banner .prompt_a {
        height: 80px;
        line-height: 80px;
        font-weight: bold;
        font-size: 24px;
        margin-left: 40px;
    }

    .banner .prompt_b {
        height: 80px;
        line-height: 90px;
        font-size: 14px;
        color: #777;
        margin-left: 40px;
    }

    .cart_line {
        width: 100%;
        height: 2px;
        background: #4f6e9d;
    }

    .list {
        width: 85%;
        margin: 0 auto;
        background-color: #f5f5f5;
        text-align: center;
    }

    .list .title {
        height: 100px;
        line-height: 100px;
        font-weight: bold;
    }

    .list .bookRow {
        height: 120px;
        line-height: 90px;
    }

    .list .bookRow .bookImg {
        width: 60px;
    }

    .summary {
        width: 85%;
        margin: 0 auto;
        height: 60px;
        line-height: 60px;
        background-color: #f5f5f5;
        text-align: center;
        margin-top: 20px;
    }

    .summary .number {
        color: #4f6e9d;
    }

    .summary .price {
        margin-right: 40px;
        float: right;
    }

    .summary .settlement {
        height: 60px;
        background-color: #4f6e9d;
        color: #ffffff;
        cursor: pointer;
    }

    .summary .settlement:hover {
        background-color: #7E9DCA;
    }
</style>