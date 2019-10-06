<template>
  <div>
    <Modal
      @on-ok="ok"
      @on-cancel="cancel"
      class="common-modal"
      :value="value">
      <p class="add-title">{{title}}</p>
      <div class="form-content">
        <Form ref="addUpdateForm" :model="addUpdateForm" :rules="addUpdateRules" :label-width="80">
          <FormItem prop="name" label="名称">
            <Input type="text" v-model="addUpdateForm.name" placeholder="名称" />
          </FormItem>
          <FormItem prop="code" label="编码">
            <Input type="text" v-model="addUpdateForm.code" placeholder="编码" />
          </FormItem>
          <FormItem prop="pcodeName" label="上级">
            <Poptip trigger="click">
              <Input type="text" :readonly="true" v-model="addUpdateForm.pcodeName" placeholder="上级" :style="{width: '340px'}"/>
              <div slot="content" class="pid-poptip">
                <Tree :data="td" @on-select-change="treeChange"></Tree>
              </div>
            </Poptip>
          </FormItem>
          <FormItem label="描述">
            <Input type="textarea" v-model="addUpdateForm.description" placeholder="描述" />
          </FormItem>
          <FormItem label="排序">
            <InputNumber v-model="addUpdateForm.seq" :min="0"></InputNumber>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button @click="cancel" type="info">取消</Button>
        <Button @click="ok" type="success">提交</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      addUpdateForm: {
        name: '',
        code: '',
        pcode: '0',
        pcodeName: '根目录',
        description: '',
        seq: 0
      },
      td: [{
        code: '0',
        title: '根目录',
        name: '根目录',
        expand: true,
        children: []
      }],
      addUpdateRules: {
        name: [
          {
            required: true, message: '名称不能为空', trigger: 'blur'
          }
        ],
        code: [
          {
            required: true, message: '编码不能为空', trigger: 'blur'
          },
          {
            validator: this.exist, trigger: 'blur'
          }
        ],
        pcodeName: [
          {
            required: true, message: '上级不能为空', trigger: 'blur'
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
    treeData: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    ...mapActions(['saveDict', 'updateDict', 'getDictById', 'existDict']),
    init (dictId) {
      this.td[0].children = this.treeData
      if (dictId) {
        this.getDictById({ dictId }).then(res => {
          this.addUpdateForm.id = res.data.dict.id
          this.addUpdateForm.code = res.data.dict.code
          this.addUpdateForm.name = res.data.dict.name
          this.addUpdateForm.title = res.data.dict.title
          this.addUpdateForm.pcode = res.data.dict.pcode
          this.addUpdateForm.title = res.data.dict.title
          this.addUpdateForm.description = res.data.dict.description
          this.addUpdateForm.seq = res.data.dict.seq
          this.setPCodeName(this.td, res.data.dict.pcode)
        }).catch(err => {
          this.$Notice.error({
            title: '错误',
            desc: err.msg
          })
        })
      }
      this.$nextTick(() => {
        this.$refs.addUpdateForm.resetFields()
      })
    },
    cancel () {
      this.clearData()
      this.$emit('input', !this.value)
    },
    ok () {
      this.$refs.addUpdateForm.validate((valid) => {
        if (valid) {
          let oper = this.addUpdateForm.id ? 'updateDict' : 'saveDict'
          this[oper](this.addUpdateForm).then(res => {
            if (res.data.code === 0) {
              this.$emit('input', !this.value)
              this.$emit('refreshDataList')
              this.clearData()
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
    clearData () {
      this.addUpdateForm = {
        seq: 0,
        pcode: '0',
        pcodeName: '根目录'
      }
    },
    treeChange (value) {
      this.addUpdateForm.pcode = value[0].code
      this.addUpdateForm.pcodeName = value[0].name
    },
    setPCodeName (treeData, pcode) {
      for (let i = 0; i < treeData.length; i++) {
        if (treeData[i].children && treeData[i].children.length > 0) {
          if (treeData[i].code === pcode) {
            this.addUpdateForm.pcodeName = treeData[i].name
            this.$set(treeData[i], 'selected', true)
            break
          } else {
            this.setPCodeName(treeData[i].children, pcode)
          }
        } else {
          if (treeData[i].code === pcode) {
            this.addUpdateForm.pcodeName = treeData[i].name
            this.$set(treeData[i], 'selected', true)
          }
        }
      }
    },
    exist (rule, value, callback) {
      if (value !== '0') {
        this.existDict({ code: value, id: this.addUpdateForm.id }).then(res => {
          if (res.data.result) {
            callback(new Error('编码已存在'))
          } else {
            callback()
          }
        }).catch(err => {
          console.log(err)
        })
      } else {
        callback(new Error('编码已存在'))
      }
    }
  }
}
</script>
<style lang="less">
  @import "~@/style/modal.less";
  .common-modal .form-content{
    padding: 20px 50px 20px 50px;
  }
  .pid-poptip{
    height: 220px;
    overflow-x: hidden;
    overflow-y: auto;
  }
</style>
