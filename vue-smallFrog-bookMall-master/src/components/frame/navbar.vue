<template>
    <el-container>
        <el-header>
            <el-row class="nav" type="flex" align="middle">
                <el-col :xs="0" :sm="2" :md="5" :lg="5" class="box hidden-xs-only"><img class="image" src="../../../static/logo.png" @click="toHome()"></el-col>
                <el-col :xs="16" :sm="13" :md="10" :lg="8">
                    <el-menu :default-active="$route.path" :router="true" mode="horizontal" active-text-color="#4F6E9D">
                        <el-menu-item index="/home">首页</el-menu-item>
                        <el-menu-item index="/category">书籍分类</el-menu-item>
                        <el-menu-item index="/newProduct">借阅规则</el-menu-item>
                        <el-menu-item index="/onSale">好书推荐</el-menu-item>
                    </el-menu>
                </el-col>
                <el-col :xs="0" :sm="2" :md="2" :lg="4" class="box hidden-md-and-down">
                    <el-input class="search1 search2 search3" placeholder="输入书名搜索书籍" active-text-color="#4F6E9D" size="small" prefix-icon="el-icon-search" v-model="input" ref="test" @keyup.enter.native="toSearch()">
                    </el-input>
                </el-col>

                <el-col :xs="0" :sm="3" :md="2" :lg="3" class="box hidden-xs-only">
                    <div v-if="this.$cookies.get('status') == 'unlogin' || !this.$cookies.get('status')">
                        <el-button class="myButton el-icon-user" @click="toLogin()" circle></el-button>
                        
                    </div>

                    <div v-if="this.$cookies.get('status') == 'logined'">
                        <el-dropdown>
                            <el-avatar :src="'../../../static/profilePhoto/' + this.$cookies.get('user').profilePhoto" @error="errorHandler"></el-avatar>

                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item icon="el-icon-setting"><label @click="setting()">个人中心</label></el-dropdown-item>
                                <el-dropdown-item icon="el-icon-circle-close"><label @click="exit()">退出登录</label></el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </el-col>

                <el-col :xs="8" :sm="4" :md="5" :lg="4">
                    <div style="display: flex;">
                        <div class="cart" @click="toCart()"><i class="el-icon-s-goods cartIcon"></i>借阅管理</div>
                        <div class="order" @click="toOrder()"><i class="el-icon-s-order orderIcon"></i>历史借阅</div>
                    </div>
                </el-col>
            </el-row>
        </el-header>

        <el-main>
            <transition mode="out-in" enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
                <keep-alive>
                    <router-view v-if="$route.meta.keepAlive"></router-view>
                </keep-alive>
            </transition>
            <transition mode="out-in" enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
                <router-view v-if="!$route.meta.keepAlive"></router-view>
            </transition>
        </el-main>
    </el-container>
</template>

<script>
import axios from "axios";
import 'element-ui/lib/theme-chalk/display.css'

export default {
  inject: ['reload'],
  data () {
    return {
      input: '',
      books: []
    }
  },
  created(){
      console.log(this.$cookies.get('user'))
  },
  methods: {
    toLogin () {
      this.$router.push({
        path: '/login'
      })
    },
    toHome () {
      this.$router.push({
        path: '/'
      })
    },
    toCart () {
      // eslint-disable-next-line eqeqeq
      if (this.$cookies.get('status') === 'logined') {
        this.$router.push({
          path: '/shopping/cart'
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
        })
      }
    },
    toOrder () {
      // eslint-disable-next-line eqeqeq
      if (this.$cookies.get('status') === 'logined') {
        this.$router.push({
          path: '/order'
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
        })
      }
    },
    setting () {
      this.$router.push({
        path: '/setting'
      }).catch(res=>{
          console.log(this.$route)
      })
    },
    exit () {
      this.$cookies.set('status', 'unlogin')
      this.$cookies.remove('user')
      this.$cookies.remove('role')
      this.$router.push({
        path: '/'
      }).catch(res=>{
          console.log(this.$route)
      })
    },
    errorHandler () {
      return true
    },
    toSearch(){
        this.$router.push({
            path: '/category',
        })
        this.$store.dispatch('bookName', this.input)
    }
  }
}
</script>

<style>
    .nav {
        top: 0;
        left: 0;
        right: 0;
        background-color: #FFFFFF;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        position: fixed;
        z-index: 999;
    }

    .el-menu.el-menu--horizontal {
        border-bottom: none;
    }

    .el-menu--horizontal .el-menu-item:not(.is-disabled):focus,
    .el-menu--horizontal .el-menu-item:not(.is-disabled):hover {
        color: #4F6E9D;
    }

    .el-dropdown-menu__item:focus,
    .el-dropdown-menu__item:not(.is-disabled):hover {
        color: #4F6E9D;
    }

    .nav .box {
        background-color: #FFFFFF;
        text-align: center;
        color: #4F6E9D;
        font-size: 20px;
        cursor: pointer;
    }

    .nav .box .image {
        width: 200px;
        margin-left: 15px;
    }

    .search1 input.el-input__inner {
        /* width: 90%; */
        /* border-color: #4F6E9D; */
        border-radius: 25px;
    }

    .search2 input.el-input__inner:hover {
        /* width: 90%; */
        border-color: #4F6E9D;
        border-radius: 25px;
    }

    .search3 input.el-input__inner:focus {
        /* width: 90%; */
        border-color: #4F6E9D;
        border-radius: 25px;
    }

    .nav .box .myButton:focus,
    .nav .box .myButton:hover {
        color: #FFFFFF;
        border-color: #7E9DCA;
        background-color: #7E9DCA;
    }

    .nav .box .myButton:active {
        color: #FFFFFF;
        border-color: #7E9DCA;
        outline: 0;
    }

    .nav .cart {
        width: 50%;
        height: 60px;
        line-height: 60px;
        text-align: center;
        color: #F5f5f5;
        background-color: #4F6E9D;
        font-size: 15px;
        cursor: pointer;
        /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
    }

    .nav .cart:hover {
        background-color: #7E9DCA;
    }

    .nav .cartIcon {
        font-size: 18px;
        margin-right: 10px;
    }

    .nav .order {
        width: 50%;
        height: 60px;
        line-height: 60px;
        text-align: center;
        color: #F5f5f5;
        background-color: #4F6E9D;
        font-size: 15px;
        cursor: pointer;
        /* box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
    }

    .nav .order:hover {
        background-color: #7E9DCA;
    }

    .nav .orderIcon {
        font-size: 18px;
        margin-right: 10px;
    }

    .footer {
        background-color: #F2F6FC;
        text-align: center;
        /* position: absolute;
        bottom: 0; */
    }

    .footer .logo {
        margin-top: 30px;
        font-size: 22px;
        font-weight: 600;
        color: #303133;
        height: 20px;
    }

    .footer .logo .favicon {
        width: 35px;
        margin-right: 10px;
    }

    .footer .description {
        text-align: justify;
        width: 70%;
        margin-left: 100px;
        color: #303133;
    }

    .footer .socialImg {
        width: 25px;
        margin-left: 10px;
        margin-right: 10px;
    }

    .footer .contact {
        color: #303133;
    }

    .footer .right {
        display: flex;
    }

    .footer dl {
        margin-top: 30px;
        margin-left: 120px;
        line-height: 40px;
        color: #303133;
    }
</style>
