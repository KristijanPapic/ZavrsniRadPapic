using Carer.Common;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Repository
{
    class PetRepository : IPetRepository
    {
        private String connectionString = "Data Source=SQL8004.site4now.net;Initial Catalog=db_a8bd39_carerdb;User Id=db_a8bd39_carerdb_admin;Password=Micamaca12";

        public async Task<List<PetDomainModel>> GetAllPetsAsync(PetFilter filter)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            StringBuilder queryString = new StringBuilder("select * from Pet");
            if (filter.OwnerId.HasValue)
            {
                queryString.Append($" where OwnerId = '{filter.OwnerId}'");
            }
            SqlCommand command = new SqlCommand(queryString.ToString(), connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet petData = new DataSet();
            await Task.Run(() => (adapter.Fill(petData)));
            List<PetDomainModel> pets = new List<PetDomainModel>();
            if (petData.Tables[0].Rows.Count == 0)
            {
                return pets;
            }
            foreach (DataRow dataRow in petData.Tables[0].Rows)
            {
                pets.Add(new PetDomainModel
                {
                    Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                    Name = Convert.ToString(dataRow["Name"]),
                    Kind = Convert.ToString(dataRow["Kind"]),
                    Breed = Convert.ToString(dataRow["Breed"]),
                    OwnerId = Guid.Parse(Convert.ToString(dataRow["OwnerId"])),
                });
            }
            return pets;
        }


        public async Task PostPetAsync(PetDomainModel pet)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"INSERT INTO Pet (Id,Name,Kind,Breed,OwnerId) VALUES ('{pet.Id}','{pet.Name}','{pet.Kind}','{pet.Breed}','{pet.OwnerId}')";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public async Task PutPetAsync(PetDomainModel pet)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE Pet SET Name = '{pet.Name}',Kind = '{pet.Kind}',Breed = '{pet.Breed}' where Id = '{pet.Id}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }


        public async Task DeletePetAsync(Guid id)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"delete from Pet where Id = '{id}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }


    }
}
