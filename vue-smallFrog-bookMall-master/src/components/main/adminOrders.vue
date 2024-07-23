<template>
    <div>
        <el-row type="flex">
            <el-col :span="4">
                <el-image src="../../../static/background.png" fit="fill"></el-image>
            </el-col>
            <el-col :offset="2">
                <el-row><h2>借阅管理</h2></el-row>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-input v-model="searchOrderId" placeholder="请输入借阅号"></el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-input v-model="searchCustomerId" placeholder="请输入用户ID"></el-input>
                    </el-col>
                    <el-col :span="6">
                        <el-row type="flex">
                            <el-col :span="10"><el-button icon="el-icon-search" type="primary" @click="searchOrdersInfo()">搜索</el-button></el-col>
                        </el-row>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        <el-row>
            <el-table :data="ordersInfo">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form inline class="demo-table-expand" label-position="left" label-width="100px">
                            <el-row type="flex">
                                <el-col :span="6"><el-form-item label="借阅号："><span>{{ props.row.id }}</span></el-form-item></el-col>
                            </el-row>
                            <el-row type="flex">
                                <el-form-item label="图书信息："></el-form-item>
                                <el-col :span="6"><el-form-item label="图书编号："><span>{{ props.row.bookId }}</span></el-form-item></el-col>
                                <el-col :span="6"><el-form-item label="图书名称："><span>{{ props.row.bookName }}</span></el-form-item></el-col>
                            </el-row>
                            <el-row type="flex">
                                <el-form-item label="用户信息："></el-form-item>
                                <el-col :span="6"><el-form-item label="用户ID："><span>{{ props.row.customerId }}</span></el-form-item></el-col>
                                <el-col :span="6"><el-form-item label="用户昵称："><span>{{ props.row.customerName }}</span></el-form-item></el-col>
                            </el-row>
                            <el-row type="flex">
                                <el-form-item label="借阅人信息："></el-form-item>
                                <el-col :span="6"><el-form-item label="借阅人："><span>{{ props.row.receiver }}</span></el-form-item></el-col>
                                <el-col :span="8"><el-form-item label="电话："><span>{{ props.row.tel }}</span></el-form-item></el-col>
                                <el-col ><el-form-item label="地址："><span>{{ props.row.address }}</span></el-form-item></el-col>
                            </el-row>
                            <!-- <el-form-item label="用户昵称"><span>{{ props.row. }}</span></el-form-item> -->
                            <el-row type="flex">
                                <el-form-item label="借阅信息："></el-form-item>
                                <el-col :span="6"><el-form-item label="借阅数量："><span>{{ props.row.num }}</span></el-form-item></el-col>
                                <el-col :span="8"><el-form-item label="押金："><span>{{ props.row.singlePrice }}</span></el-form-item></el-col>
                                <el-col><el-form-item label="应收："><span>{{ props.row.totalPrice }}</span></el-form-item></el-col>
                            </el-row>
                            <el-row type="flex">
                                <el-col :span="6" :offset="18"><el-form-item label="借阅日期："><span>{{ props.row.date }}</span></el-form-item></el-col>
                            </el-row>
                            <el-row type="flex">
                                <el-col :span="6" :offset="18"><el-form-item label="归还日期："><span>{{ props.row.endDate }}</span></el-form-item></el-col>
                            </el-row>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column header-align="center" align="center" prop="id" label="借阅号" width="120" fixed="left"></el-table-column>
                <el-table-column header-align="center" align="center" prop="bookName" label="书名"  width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="bookImgUrl" label="图片" width="120">
                    <template slot-scope="scope">
                        <el-image :src="'../../../static/booksImage/' + scope.row.bookImgUrl" style="height:80px; "></el-image>
                    </template>
                </el-table-column>
                <el-table-column header-align="center" align="center" prop="num" label="数量" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="totalPrice" label="押金" width="120"></el-table-column>
                <el-table-column header-align="center" align="center" prop="state" label="借阅状态" width="120" :filters="orderState" :filter-method="filterHandler"></el-table-column>
                <el-table-column header-align="center" align="center" prop="bookType" label="图书类型" width="200" :filters="bookType" :filter-method="filterHandlerBookType"></el-table-column>
                <!-- <el-table-column header-align="center" align="center" prop="description" label="备注" ></el-table-column> -->
                <el-table-column header-align="center" align="center" label="操作" fixed="right">
                    <template slot-scope="scope">
                        <el-button @click.native.prevent="editOrderInfo(scope.$index, scope.row)" size="small" v-show="scope.row.state == '待发货'" type="primary"> 确认借阅 </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>
    </div>
</template>

<script>
import 'element-ui/lib/theme-chalk/display.css'
import axios from 'axios'
export default {
    data(){
        return{
            ordersInfo:[{

            }],
            load: 0,
            orderState: [{
                text: '待付款', value: '待付款'}, 
                {text: '待发货', value: '待发货'}, 
                {text: '待收货', value: '待收货'}, 
                {text: '已完成', value: '已完成'},
            ],
            searchOrderId:"",
            searchCustomerId:"",
            bookType: [{
                value: '文学类',text: '文学类'}, 
                {value: '童书',text: '童书'}, 
                {value: '教育类',text: '教育类'}, 
                {value: '考试类',text: '考试类'}, 
                {value: '人文社科类',text: '人文社科类'},
                {value: '经管综合类',text: '经管综合类'},
                {value: '生活类',text: '生活类'},
                {value: '艺术类',text: '艺术类'},
                {value: '科技类',text: '科技类'},
                {value: '其他',text: '其他'}
            ],
        }
    },
    created(){
        this.getAllOrdersInfo()
    },
    methods:{
        getAllOrdersInfo(){
            axios.post("/getAllOrdersInfo").then(res=>{
                this.ordersInfo = res.data.data
            })
        },
        editOrderInfo(index, row){
            console.log(row)
            row.state = "待收货"
            axios.post("/editOrderInfo", row).then(res=>{
                console.log("success");
            })
        },
        searchOrdersInfo(){
            axios({
                method: 'get',
                url: "/searchOrdersInfoByOrderIdOrCustomerId",
                params:{
                    'id': this.searchOrderId,
                    'CustomerId': this.searchCustomerId,
                }
            }).then(res=>{
                console.log(res.data.data)
                this.ordersInfo = res.data.data
                console.log("success!!!!")
            })
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        },
        filterHandlerBookType(lable, row, column){
            const property = column['property'];
            return row[property] === lable;
        },
    }
}
</script>

<style>

</style>