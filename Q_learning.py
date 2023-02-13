import random

import numpy as np
import pandas as pd


class QLearningTable:
    def __init__(self, actions, states,learning_rate=0.1, reward_decay=0.9, e_greedy=0.9):# 初始化
        self.actions = actions  # a list    对应的就是行为列表很长
        self.lr = learning_rate
        self.gamma = reward_decay
        self.epsilon = e_greedy
        self.states = states
        self.q_table = pd.DataFrame(index=self.states,columns=self.actions, dtype=np.float64).fillna(0) # 初始Q表为0

    def choose_action_train(self,state_now,R_Table):# 行为选择，随机选择状态

        action_list = []
        R_Cols = R_Table.columns
        # 可选行为集合
        for i in R_Cols:
            if(R_Table.loc[state_now,i] != 0):
                action_list.append(i)


        print(action_list)
        action =  random.choice(action_list) # 在可行的列表中，随机选择一个行为

        return action

    def learn(self, s, a, r, s_):# 现状态 行为 奖励 下一个状态

        # 找到Q表的对应值，进行更新
        self.q_table.loc[s, a] =  r + self.gamma * self.q_table.loc[s_, :].max()



    def state_transition(self,state,action):

        temp = action.split('-')
        # 字符串替换
        # 替换也得改...............
        state = state.replace(temp[1],temp[0])
        state_t = list(state)
        state_t.sort()
        state = ''.join(state_t)

        # 新状态已经完成转移
        return state



def Initialization_R(STATES, ACTIONS):
    # 建立R矩阵,每一次都会重新建立这个矩阵
    R_Table = pd.DataFrame(index=STATES, columns=ACTIONS, dtype=np.float64).fillna(0)  # 初始R表为-1，奖励矩阵
    # 进行R矩阵的奖励填充
    R_Rows = R_Table.index
    R_Cols = R_Table.columns

    #进行填充
    Letters = ['D', 'E', 'A', 'B', 'C']
    for i in Letters: # 五个字母
        for j in R_Rows: # 行标
            j_temp = list(j)
            if i not in j_temp: #
                #  这里还得改----

                for k in j_temp:
                    str = i + "-" + k
                    R_Table.loc[j, str] = 10

    #
    return R_Table


def Attack_Mod(letter,R_Table):

    # 行
    R_Rows = R_Table.index
    R_Cols = R_Table.columns

    for i in R_Rows:
        # 受到攻击的微服务 进行减少
        R_Table.loc[i,R_Cols.str.startswith(letter)] -= 1

    return R_Table

    # 受到攻击了,R表更新



if __name__ == '__main__':
    """
    ABCDE/ 各对应一个微服务
    
    """

    ACTIONS = ['A-B', 'A-C', 'A-D', 'A-E', 'B-A', 'B-C', 'B-D', 'B-E', 'C-A', 'C-B', 'C-D', 'C-E', 'D-A', 'D-B', 'D-C',
               'D-E', 'E-A', 'E-B', 'E-C', 'E-D'] # 所有的行为
    STATES = ['ABC','ABD','ABE','ACD','ACE','ADE','BCD','BCE','BDE','CDE'] #所有的状态
    EPSILON = 0.9  # 学习率
    LANBDA = 0.9  # 衰减因子
    MAX_EPISODES = 100 # 迭代次数

    # 建立 QLearningTable对象
    q_1 = QLearningTable(ACTIONS,STATES,0.1,0.9,0.9)

    R_Table = Initialization_R(STATES,ACTIONS)

    print(R_Table)

    # 进行R矩阵的奖励填充
    R_Rows = R_Table.index
    R_Cols = R_Table.columns

    #         已经将R表创建完成,进行Q表的训练过程
    # 模拟第一个状态
    S = 'ABC'
    for i in range(MAX_EPISODES):
        # 可行的行为进行随机选择
        action = q_1.choose_action_train(S,R_Table)
        r = R_Table.loc[S,action]
        # 根据选择的行为，进行状态转换
        S_ = q_1.state_transition(S,action)
        # 更新Q表
        q_1.learn(S,action,r,S_)
        # print(q_1.q_table)
        # 状态转移
        S = S_


    print(q_1.q_table)









