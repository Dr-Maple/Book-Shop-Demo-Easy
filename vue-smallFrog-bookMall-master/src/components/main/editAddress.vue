<template>
    <div class="addressDiv">
        <el-row >
          <el-col :span="4" :offset="17">
              <el-button type="primary" icon="el-icon-plus" @click="addAddressDialogClick">添加收货地址</el-button>
          </el-col>
        </el-row>
        <el-table :data="address" style="width: 70%;margin: auto;margin-top: 20px;" :default-sort = "{prop: 'isDefault', order: 'ascending'}">
            <el-table-column prop="receiver" label="收件人" width="150"></el-table-column>
            <el-table-column prop="tel" label="电话" width="150"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="isDefault" label="是否默认" width="120" sortable>
                <template slot-scope="scope">
                    <el-tag size="medium" v-show="scope.row.flagDefault">默认</el-tag></template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button @click="editAddress(scope.row)" type="primary" size="small" icon="el-icon-edit" circle></el-button>
                    <el-button @click="deleteAddress(scope.row)" type="danger"  size="small" icon="el-icon-delete" circle></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="addressDialogTitle" :visible.sync="addAddressDialog" width="41%">
            <el-form label-width="130px" :model="addAddress"  label-position="right"  ref="addAddress" :rules="rules">
              <el-form-item label="收件人：" prop="receiver"><el-input v-model="addAddress.receiver" placeholder="请输入收件人姓名"></el-input></el-form-item>
              <el-form-item label="电话：" prop="tel"><el-input v-model="addAddress.tel" placeholder="请输入电话"></el-input></el-form-item>
              <el-form-item label="省/市/区：">
                <area-select :level='2' type="text"  v-model="selectedOptions" :data="pcaa" @change="addressChange" style="text-align: center;" ref="selectedOptions"></area-select>
              </el-form-item>
              <el-form-item label="详细地址："><el-input v-model="addAddress.detailAddress" placeholder="请输入详细地址"></el-input></el-form-item>
              <el-row>
                <el-col :span="6">
                    <el-form-item label="设为默认地址：">
                  <el-switch v-model="addAddress.flagDefault" active-value="true" inactive-value="false"></el-switch>
                </el-form-item>
                </el-col>
                <el-col :span="6" :offset="10"><el-form-item><el-button type="primary" @click="submitAddress('addAddress')">完成</el-button></el-form-item></el-col>
              </el-row>
              
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";
import 'element-ui/lib/theme-chalk/display.css';
import 'vue-area-linkage/dist/index.css'
import { pca, pcaa } from 'area-data';
import { regionData,CodeToText } from 'element-china-area-data'
export default {
    data(){
        let checkReceiver = (rule, value, callback) =>{
            if(value == ''){
                callback(new Error('收件人姓名不能为空'))
            }else{
                callback()
            }
        }
        let checkTel = (rule, value, callback) => {
            if (!this.checkMobile(value)) {
                callback(new Error('请输入正确的手机号'))
            } else {
                callback() 
            }
        }
        let checkPcd = (rule, value, callback) => {
            if(value == ''){
                callback(new Error('省市区不能为空'))
            }else{
                callback()
            }
        }
        return{
            options: regionData,
            
            address:[],
            selectedOptions:[],
            pca: pca,
            pcaa: pcaa,
            addAddress:{
                receiver:"",
                tel:"",
                pcd: "",
                detailAddress:"",
                flagDefault:"",
            },
            addAddressDialog: false,
            addressDialogTitle: "",
            editAddressId:"",
            pcdIsShow: true,

            rules:{
                receiver:[{
                    validator:checkReceiver,
                    trigger: 'change'
                }],
                tel:[{
                    validator: checkTel,
                    trigger: 'change'
                }],
                pcd:[{
                    validator: checkPcd,
                    trigger: 'change'
                }]
            }
        }
    },
    created(){
        this.getAllAddressInfo()
        console.log(pcaa)
    },
    methods:{
        // 验证手机号
        checkMobile (str) {
            let re = /^1\d{10}$/
            if (re.test(str)) {
                return true
            } else {
                return false
            }
        },
        handleChange(){
            
        },
        addAddressDialogClick(){
            this.addressDialogTitle = "添加收货地址"
            this.addAddressDialog = true
            this.addAddress.receiver = ""
            this.addAddress.tel = ""
            this.addAddress.pcd = ""
            this.addAddress.detailAddress = ""
            this.addAddress.flagDefault = "false"
            this.selectedOptions = []
            this.pcaa = pcaa
            this.$forceUpdate()
        },
        //省市区函数
        addressChange () {
            // console.log(this.selectedOptions)
            this.addAddress.pcd = this.selectedOptions[0] +this.selectedOptions[1]+this.selectedOptions[2]
            console.log(this.addAddress)
        },
        getAllAddressInfo(){
            // this.addressLoad = false
            axios.post("/getCustomerAddress",{
                id: this.$cookies.get('user').id
            }).then(res=>{
                this.address = JSON.parse(JSON.stringify(res.data.data))
                console.log(this.address)
            })
            console.log(this.address)
        },
        editAddress(row) {
            this.addressDialogTitle = "编辑收货地址"
            this.editAddressId = row.id
            this.addAddressDialog = true
            this.addAddress.receiver = row.receiver
            this.addAddress.tel = row.tel
            // console.log(row)

            var reg = /.+?(省|市|自治区|自治州|县|区|區)/g;
            var arr = row.address.match(reg);
            // console.log(arr)
            
            this.$set(this.selectedOptions, 0, arr[0])
            this.$set(this.selectedOptions, 1, arr[1])
            this.$set(this.selectedOptions, 2, arr[2])
            // 截取剩余的字符串 放在详细地址input框中
            var start = row.address.indexOf(arr[arr.length-1])
            start = start + arr[arr.length-1].length
            this.addAddress.detailAddress = row.address.substring(start,row.address.length)
            this.addAddress.flagDefault = row.flagDefault +""
            this.$forceUpdate()
        },
        deleteAddress(row){
            axios.post("/deleteAddress",row).then(res=>{
                if(res.data.flag){
                    this.getAllAddressInfo()
                }
            })
        },
        submitAddress(formName){
            console.log(this.addressDialogTitle)
            this.$refs[formName].validate(valid =>{
                if(valid){
                    if(this.addressDialogTitle == "添加收货地址"){
                        axios.post("/addAddress",{
                            receiver: this.addAddress.receiver,
                            tel: this.addAddress.tel,
                            address: this.addAddress.pcd + this.addAddress.detailAddress,
                            customerId: this.$cookies.get('user').id,
                            flagDefault: this.addAddress.flagDefault
                        }).then(res=>{
                            if(res.data.flag){
                                // console.log("添加成功")
                                this.$refs['addAddress'].resetFields();
                                this.getAllAddressInfo()
                            }
                        })
                    }else if(this.addressDialogTitle == "编辑收货地址"){
                        axios.post("/updateAddress",{
                            receiver: this.addAddress.receiver,
                            tel: this.addAddress.tel,
                            address: this.addAddress.pcd + this.addAddress.detailAddress,
                            customerId: this.$cookies.get('user').id,
                            id: this.editAddressId,
                            flagDefault: this.addAddress.flagDefault
                        }).then(res=>{
                            if(res.data.flag){
                                // console.log("修改成功")
                                this.addAddress.receiver = ""
                                this.addAddress.tel=""
                                this.addAddress.pcd=""
                                this.addAddress.detailAddress=""
                                this.getAllAddressInfo()
                            }
                        })
                    }
                    this.addAddressDialog = false
                }else{
                    console.log("添加失败，请稍后重试")
                    return false
                }
            })
        },
    }
}
</script>

<style>
.addressDiv{
    margin: auto;
    /* text-align: center; */
    
}
.addAddress{
    text-align: right;
}
.area-select .area-selected-trigger{
    padding: 0px 0px 0px 0px;
}
</style>