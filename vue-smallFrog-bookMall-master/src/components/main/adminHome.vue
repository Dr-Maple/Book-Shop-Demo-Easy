<template>
    <el-container>
        <el-header>
            <el-row class="nav" type="flex" align="middle" :gutter="20">
                <el-col :xs="0" :sm="2" :md="5" :lg="5" class="box hidden-xs-only">
                    <img class="image" src="../../../static/logo.png">
                </el-col>
                <el-col :offset="16">
                     {{$cookies.get('user').username}}    
                    
                </el-col>
                <el-col >
                    <el-dropdown>
                        <el-avatar :src="'../../../static/profilePhoto/' + $cookies.get('user').profilePhoto" :key="$cookies.get('user').profilePhoto"></el-avatar>

                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-setting"><label @click="setting()">个人中心</label></el-dropdown-item>
                            <el-dropdown-item icon="el-icon-circle-close"><label @click="exit()">退出登录</label></el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-col>
            </el-row>
        </el-header>
        <el-container >
            
            <el-aside width="250px" >
                <el-card class="leftNav">
                    <img :src="'../../../static/profilePhoto/' + $cookies.get('user').profilePhoto" class="leftImg" />
                    <div class="navItem" :class="index == showIndex ? 'cur' : ''" v-for="(item, index) in navItems" :key="index" @click="showInfo(index)">{{ item }}</div>
                </el-card> 
            </el-aside>
            <el-main>
                <div>
                    <router-view></router-view>
                </div>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import 'element-ui/lib/theme-chalk/display.css'
import axios from 'axios'
export default {
    data(){
        return{
            navItems : ["书籍管理", "借阅管理","管理员信息","新增管理员"],
            showIndex: 0,
            
        }
    },
    created(){
    },
    methods:{
        showInfo(e){
            this.showIndex = e
            if(e == 0){
                this.$router.push({
                    path: "/adminHome/adminBooks"
                })
            }else if(e == 1){
                this.$router.push({
                    path: "/adminHome/adminOrders"
                })
            }else if(e == 2){
                this.$router.push({
                    path: "/adminHome/adminInfo"
                })
            }else if(e == 3){
                this.$router.push({
                    path: "/adminHome/addAdmin"
                })
            }
        },
        setting(){
            this.$router.push({
                path: '/adminHome'
            }).catch(res=>{
                console.log(this.$route)
            })
        },
        exit(){
            this.$cookies.set('status', 'unlogin')
            this.$cookies.remove('user')
            this.$cookies.remove('role')
            this.$router.push({
                path: '/'
            }).catch(res=>{
                console.log(this.$route)
            })
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
    padding-left: 80px;
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
.leftNav {
    top: 50%;
    width: 150px;
    height: 370px;
    margin-top: -180px;
    margin-left: 60px;
    position: fixed;
    border-radius: 20px;
    background-color: #4f6e9d;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}

.leftNav .leftImg {
    width: 100%;
    margin-bottom: 30px;
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

</style>

