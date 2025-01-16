<template>
  <BasicModal @register="registerModal" v-bind="$attrs" :title="title" :helpMessage="helpMessage" @ok="handleSubmit">
    <BasicTree class="" :title="title" toolbar checkable search :treeData="treeData" :beforeRightClick="getRightMenuList" :expandedKeys="tenantAreas" :checkedKeys="tenantAreas" ref="treeRef"/>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicTree, ActionItem, ContextMenuItem, TreeActionType } from '/@/components/Tree/index';
  // import { treeData } from '../data';
  import { getAreaTree } from '/@/api/common/api';
  import { setTenantAreas } from '../tenant.api';
  import { cloneDeep } from 'lodash-es';

  //设置弹窗标题
  const title = '租户授权区域';
  const helpMessage = ['设置租户授权区域'];
  const tenantId = ref<number>(0);
  const treeData = ref<any[]>([]);
  const tenantAreas = ref<any[]>([]);
  export default defineComponent({
    components: { BasicModal, BasicTree },
    setup() {
      treeData.value = [];
      const treeRef = ref<Nullable<TreeActionType>>(null);
      //表单赋值
      const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
        tenantId.value = data.tenantId;
        getAreaTree({'deep':3,'tenantId':tenantId.value}).then((res) => {
          treeData.value = res.areaTree;
          let tenantAreaKeys = [];
          res.tenantAreas.forEach((item) => {
            tenantAreaKeys.push(item.area_code);
          });
          tenantAreas.value = tenantAreaKeys;
        });
        // setModalProps({ minHeight: 100 });
      });

      function getTree() {
        const tree = unref(treeRef);
        if (!tree) {
          throw new Error('tree is null!');
        }
        return tree;
      }

      /**
       * 提交，返回给租户list页面
       */
      async function handleSubmit() {
        // let values = await validate();
        // emit('inviteOk',values.phone);
        const keys = getTree().getCheckedKeys();
        let params={};
        params.tenantId=unref(tenantId);
        params.areas=keys;
        setTenantAreas(params);
        // closeModal();
      }

      function handlePlus(node: any) {
        console.log(node);
      }

      function getRightMenuList(node: any): ContextMenuItem[] {
        return [
          {
            label: '新增',
            handler: () => {
              console.log('点击了新增', node);
            },
            icon: 'bi:plus',
          },
          {
            label: '删除',
            handler: () => {
              console.log('点击了删除', node);
            },
            icon: 'bx:bxs-folder-open',
          },
        ];
      }
      const actionList: ActionItem[] = [
        {
          // show:()=>boolean;
          render: (node) => {
            return h(PlusOutlined, {
              class: 'ml-2',
              onClick: () => {
                handlePlus(node);
              },
            });
          },
        },
        {
          render: () => {
            return h(DeleteOutlined);
          },
        },
      ];

      function createIcon({ level }) {
        if (level === 1) {
          return 'ion:git-compare-outline';
        }
        if (level === 2) {
          return 'ion:home';
        }
        if (level === 3) {
          return 'ion:airplane';
        }
        return '';
      }
      return { title, helpMessage, treeData, tenantAreas, actionList, treeRef, registerModal, handleSubmit, getRightMenuList, createIcon };
    },
  });
</script>
<style scoped></style>