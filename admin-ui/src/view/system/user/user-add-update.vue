<template>
  <Modal :value="value"
    @on-ok="ok"
    @on-cancel="cancel"
    class="common-modal">
    <p class="add-title">{{title}}</p>
    <div class="form-content">
      <Form ref="addUpdateForm" :model="addUpdateForm" :rules="addUpdateRules" :label-width="80">
        <FormItem prop="username" label="用户名">
          <Input type="text" v-model="addUpdateForm.username" placeholder="用户名" :readonly="!validate">
          </Input>
        </FormItem>
        <FormItem prop="password" label="密码" >  <!-- v-if="validate" -->
          <Input type="password" v-model="addUpdateForm.password" placeholder="密码">
          </Input>
        </FormItem>
        <FormItem prop="name" label="昵称">
          <Input type="text" v-model="addUpdateForm.name" placeholder="昵称">
          </Input>
        </FormItem>
        <FormItem prop="roleIdList" label="角色" placeholder="角色">
          <Select v-model="addUpdateForm.roleIdList" multiple >
            <Option v-for="item in roleData" :value="item.roleId" :key="item.roleId">{{ item.roleName }}</Option>
        </Select>
        </FormItem>
        <FormItem prop="status" label="状态">
          <i-switch size="large" v-model="addUpdateForm.status" :true-value="1" :false-value="0">
            <span slot="open">正常</span>
            <span slot="close">锁定</span>
          </i-switch>
        </FormItem>
      </Form>
    </div>
    <div slot="footer">
      <Button @click="cancel" type="info">取消</Button>
      <Button @click="ok" type="success">提交</Button>
    </div>
  </Modal>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  name: 'userAddUpdate',
  data () {
    return {
      addUpdateForm: {
        username: '',
        password: '',
        name: '',
        roleIdList: '',
        status: 1
      },
      validate: true,
      addUpdateRules: {
        username: [
          {
            required: true, message: '用户名不能为空', trigger: 'blur'
          },
          {
            validator: this.validateUsername, trigger: 'blur'
          }
        ],
        password: [
          {
            required: true, message: '密码能为空', trigger: 'blur'
          },
          {
            min: 6, message: '密码至少6位', trigger: 'blur'
          }
        ],
        name: [
          {
            required: true, message: '昵称不能为空', trigger: 'blur'
          }
        ],
        roleIdList: [
          {
            required: true, type: 'array', message: '角色不能为空', trigger: 'change'
          }
        ]
      }
    }
  },
  props: {
    value: {
      type: Boolean,
      required: true,
      default: false
    },
    title: {
      type: String,
      default: '新增'
    },
    roleData: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    ...mapActions(['validaUsername', 'save', 'update', 'getUserById']),
    init (userId) {
      if (userId) {
        this.validate = false
        this.getUserById({ userId }).then(res => {
          this.addUpdateForm.userId = res.data.user.userId
          this.addUpdateForm.username = res.data.user.username
          this.addUpdateForm.password = res.data.user.password
          this.addUpdateForm.salt = res.data.user.salt
          this.addUpdateForm.name = res.data.user.name
          this.addUpdateForm.roleIdList = res.data.user.roleIdList
          this.addUpdateForm.status = res.data.user.status
        }).catch(err => {
          this.$Notice.error({
            title: '错误',
            desc: err.msg
          })
        })
      } else {
        this.validate = true
      }
      this.$nextTick(() => {
        this.$refs.addUpdateForm.resetFields()
      })
    },
    cancel () {
      this.addUpdateForm = {}
      this.$emit('input', !this.value)
    },
    ok () {
      this.$refs.addUpdateForm.validate((valid) => {
        if (valid) {
          let oper = this.addUpdateForm.userId ? 'update' : 'save'
          this[oper](this.addUpdateForm).then(res => {
            if (res.data.code === 0) {
              this.$emit('input', !this.value)
              this.$emit('refreshDataList')
              this.addUpdateForm = {}
              this.$Notice.success({
                title: '成功',
                desc: '操作成功'
              })
            }
          }).catch(err => {
            this.$Notice.error({
              title: '错误',
              desc: err.msg
            })
          })
        } else {
          this.$Notice.warning({
            title: '警告',
            desc: '请完善信息'
          })
        }
      })
    },
    validateUsername (rule, value, callback) {
      if (this.validate) {
        this.validaUsername({ username: value }).then(res => {
          if (res.data.result) {
            callback()
          } else {
            callback(new Error('用户名已存在'))
          }
        }).catch(err => {
          console.log(err)
        })
      } else {
        callback()
      }
    }
  }
}
</script>
<style lang="less">
  @import "~@/style/modal.less";
</style>
