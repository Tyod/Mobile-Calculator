package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding;
    private var resultado : Float = 0.0F;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var flag: Boolean = false

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //BUTÕES CALCULADORA

        //NUMEROS
        binding.btn0.setOnClickListener{
            if(binding.textViewMostrador2.text != "0")
                binding.textViewMostrador2.append("0")
        }

        binding.btn1.setOnClickListener{
                binding.textViewMostrador2.append("1")
        }

        binding.btn2.setOnClickListener{
                binding.textViewMostrador2.append("2")
        }

        binding.btn3.setOnClickListener{
                binding.textViewMostrador2.append("3")
        }

        binding.btn4.setOnClickListener{
                binding.textViewMostrador2.append("4")
        }

        binding.btn5.setOnClickListener{
                binding.textViewMostrador2.append("5")
        }

        binding.btn6.setOnClickListener{
                binding.textViewMostrador2.append("6")
        }

        binding.btn7.setOnClickListener{
                binding.textViewMostrador2.append("7")
        }

        binding.btn8.setOnClickListener{
                binding.textViewMostrador2.append("8")
        }

        binding.btn9.setOnClickListener{
                binding.textViewMostrador2.append("9")
        }




        //OPERADORES
        binding.btnSoma.setOnClickListener{
                binding.textViewMostrador2.append("+")
        }

        binding.btnSubtracao.setOnClickListener{
                binding.textViewMostrador2.append("-")
        }

        binding.btnVezes.setOnClickListener{
                binding.textViewMostrador2.append("x")
        }

        binding.btnDivisao.setOnClickListener{
                binding.textViewMostrador2.append("/")
        }

        binding.btnClear.setOnClickListener{
            if(flag) {
                binding.textViewMostrador1.append(resultado.toString())
                binding.textViewMostrador2.text = ""
                flag=false
            }

                binding.textViewMostrador2.append("")
        }


        //Utilizade
        binding.btnDEL.setOnClickListener{

            if(binding.textViewMostrador2.text.isBlank())
                binding.textViewMostrador2.text = "";
            else
                binding.textViewMostrador2.text = binding.textViewMostrador2.text.substring(0 ,binding.textViewMostrador2.text.length-1)
        }

        binding.btnAC.setOnClickListener{
            binding.textViewMostrador2.text = ""
            binding.textViewMostrador1.text = ""
        }

        binding.btnVirgula.setOnClickListener{

            binding.textViewMostrador2.append(".")
        }

        binding.btnIgual.setOnClickListener{
            binding.textViewMostrador1.text = binding.textViewMostrador2.text
            binding.textViewMostrador1.append("=")
            binding.textViewMostrador2.text = CalculateResults()
            flag = true
        }

        binding.btnExit.setOnClickListener{
            exitProcess(0)
        }

    }

    private fun CalculateResults(): String {

        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""
        
        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        resultado = result

        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>) : Float {
        var result = passedList[0] as Float


        for(i in passedList.indices){
            if(passedList[i] is Char && i!= passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                if(operator == '+')
                    result += nextDigit
                if(operator == '-')
                    result -= nextDigit
            }
        }
        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList

        while(list.contains('x') || list.contains('/')){
            list = calcTimesDiv(list)
        }

        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {

        val newList = mutableListOf<Any>()

        var restartIndex = passedList.size

        for(i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float
                when(operator){
                    'x'->{
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i+1
                    }
                    '/'->{
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i+1
                    }
                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if(i>restartIndex)
                newList.add(passedList[i])
        }

        return newList

    }

    private fun digitsOperators() : MutableList<Any>{
        val list = mutableListOf<Any>()

        var currentDigit = ""
        for(character in binding.textViewMostrador2.text){
            if(character.isDigit() || character == '.')
                currentDigit += character
            else{
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return  list
    }
}