'use client'

import { DisciBox } from "@/components/disciplinas/DisciBox"
import { Actions } from "@/components/disciplinas/Actions"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { useQuery } from "@tanstack/react-query"
import { Disciplina } from "@/types/Disciplina"
import { getDisciplinaByProfessor, getDisciplinas } from "@/utils/api"
import { BoxSkeleton } from "@/components/Skeletons/BoxSkeleton"

const Page = () => {

    const router = useRouter();
    const token = localStorage.getItem('authToken')
    const [filtro, setFiltro] = useState('')
    const userTipo = localStorage.getItem('tipoUser')

    useEffect(() => {
        if (!token) {
            router.push('/login');
        }
  }, [router]);

    const { data: disciplinas, isLoading } = useQuery<Disciplina[]>({
        queryKey: ['disciplinas', token],
        queryFn: userTipo === 'PROFESSOR' ? () => getDisciplinaByProfessor(404) : getDisciplinas,
        enabled: !!token
    })

    const disciplinasFiltradas = disciplinas?.filter((disciplina) => {
        const data = (disciplina.nomeDaDisciplina || "").toLowerCase().includes(filtro.toLowerCase())
        return data
    })

    return(
        <main className="px-5 w-full">
            <Card className="">
                <CardHeader>
                    <CardTitle>Lista de Disciplinas</CardTitle>
                    <div className="h-1 w-full bg-primary"></div>
                </CardHeader>
                <CardContent>
                    <Actions onFiltroChange={setFiltro}/>

                    <div className="mt-2">Total de Disciplinas: {disciplinasFiltradas?.length}</div>

                    <div className="grid grid-cols-1 mt-5 gap-6 sm:grid-cols-2 md:grid-cols-3 xl:grid-cols-4">
                        {isLoading &&
                            <BoxSkeleton />
                        }

                        {!isLoading &&
                            <>
                                {disciplinasFiltradas?.map((item, index) => (
                                <DisciBox key={index} data={item} />
                                ))
                                }
                            </>
                        }
                        
                    </div>
                </CardContent>
            </Card>
        </main>
    )
}

export default Page
